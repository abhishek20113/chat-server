package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protos.*;

import java.util.Iterator;

public class ReceiverClient {
  public static void main(String[] args) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
      .usePlaintext()
      .build();
    ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(managedChannel);

    LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
      .setUserId("pulkit")
      .setPassword("qwedsa")
      .build());
    String token = loginResponse.getJwtToken();
    assert token != null;

    Iterator<Message> messageIterator = stub.receiveMessage(ReceiveMessageRequest.newBuilder()
      .setJwtToken(token)
      .build());
    while (messageIterator.hasNext()) {
      System.out.println(messageIterator.next());
    }
  }
}
