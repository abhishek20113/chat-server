package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protos.*;

import java.util.Iterator;

public class LoginTest {
  public static void main(String[] args) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
      .usePlaintext()
      .build();
    ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(managedChannel);

    // Correct Login
    LoginResponse loginResponse = stub.login(LoginRequest.newBuilder()
      .setUserId("pulkit")
      .setPassword("qwedsa")
      .build());
    System.out.println(loginResponse);

    // Incorrect Login
    loginResponse = stub.login(LoginRequest.newBuilder()
      .setUserId("pulkit")
      .setPassword("abced")
      .build());
    System.out.println(loginResponse);
  }
}
