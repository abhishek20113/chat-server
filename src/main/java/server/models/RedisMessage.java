package server.models;

public class RedisMessage {

  private String fromUserId;
  private String message;

  public RedisMessage(String fromUserId, String message) {
    this.fromUserId = fromUserId;
    this.message = message;
  }

  public String fromUserId() {
    return fromUserId;
  }

  public String message() {
    return message;
  }
}
