package server.service;

import com.google.common.collect.Maps;
import io.grpc.stub.StreamObserver;
import protos.Message;
import redis.clients.jedis.Jedis;
import server.models.RedisMessage;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static server.util.ApplicationConstants.FieldConstants.*;
import static server.util.ApplicationConstants.GSON;

public class MessageService {

  private Map<String, StreamObserver<Message>> observers;
  private Jedis jedis;

  public MessageService(Jedis jedis) {
    this.observers = Maps.newConcurrentMap();
    this.jedis = jedis;
  }

  public boolean sendMessage(String fromUserId, String toUserId, String message) {
    if (message.length() > MAX_MESSAGE_SIZE || isRateLimitExceeded(fromUserId))
      return false;

    if (!isNull(observers.get(toUserId))) {
      sendMessageOnStream(observers.get(toUserId), fromUserId, message);
      return true;
    } else {
      persistMessage(fromUserId, toUserId, message);
      return false;
    }
  }

  private void persistMessage(String fromUserId, String toUserId, String message) {
    String messageKey = format(MESSAGE_KEY_FORMAT, toUserId);
    jedis.rpush(messageKey, GSON.toJson(new RedisMessage(fromUserId, message)));
    while (jedis.llen(messageKey) > PERSISTED_MESSAGE_COUNT) {
      jedis.lpop(messageKey);
    }
  }

  private boolean isRateLimitExceeded(String userId) {
    String messageTimeKey = format(MESSAGE_TIME_KEY_FORMAT, userId);
    long currentTimeMillis = System.currentTimeMillis();
    while (jedis.llen(messageTimeKey) >= 3) {
      Long timestamp = Long.valueOf(jedis.lrange(messageTimeKey, 0, 0).get(0));
      if (currentTimeMillis - timestamp > 5 * 1000) {
        jedis.lpop(messageTimeKey);
      } else {
        break;
      }
    }
    if (jedis.llen(messageTimeKey) < 3) {
      jedis.rpush(messageTimeKey, String.valueOf(currentTimeMillis));
      return false;
    } else {
      return true;
    }
  }

  public void openReceiverStream(String userId, StreamObserver<Message> stream) {
    String messageKey = format(MESSAGE_KEY_FORMAT, userId);
    while (jedis.llen(messageKey) > 0) {
      String redisMessage = jedis.lpop(messageKey);
      RedisMessage rMessage = GSON.fromJson(redisMessage, RedisMessage.class);
      sendMessageOnStream(stream, rMessage.fromUserId(), rMessage.message());
    }
    observers.put(userId, stream);
  }

  private void sendMessageOnStream(StreamObserver<Message> stream, String fromUserId, String message) {
    stream.onNext(Message.newBuilder()
      .setFrom(fromUserId)
      .setMessage(message)
      .build()
    );
  }
}
