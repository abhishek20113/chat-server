package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import redis.clients.jedis.Jedis;
import server.service.ChatServer;

import java.io.IOException;

public class Driver {
  public static void main(String[] args) throws InterruptedException, IOException {
    Jedis jedis = new Jedis("localhost");
    jedis.auth("redis-pwd");
    Server server = ServerBuilder.forPort(8900)
      .addService(new ChatServer(jedis))
      .build();
    server.start();
    server.awaitTermination();
  }
}
