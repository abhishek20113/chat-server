package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protos.*;

import java.util.Iterator;

public class SenderClient {
  public static void main(String[] args) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
      .usePlaintext()
      .build();
    ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(managedChannel);

    LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
      .setUserId("ankur")
      .setPassword("abcdef")
      .build());
    String token = loginResponse.getJwtToken();
    System.out.println(token);
    assert token != null;

    SendMessageResponse sendMessageResponse = stub.sendMessage(SendMessageRequest.newBuilder()
      .setJwtToken(token)
      .setRecipientUserId("pulkit")
      .setMessage("Hello for gRPC")
      .build());
    System.out.println(sendMessageResponse);
  }
}
