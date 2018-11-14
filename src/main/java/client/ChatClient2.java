package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protos.*;

import java.util.Iterator;

public class ChatClient2 {
  public static void main(String[] args) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
      .usePlaintext(true)
      .build();
    ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(managedChannel);

    SendMessageResponse sendMessageResponse = stub.sendMessage(SendMessageRequest.newBuilder()
      .setJwtToken("dqwdwd")
      .setRecipientUserId("pulkit")
      .setMessage("Hey")
      .build());
    System.out.println(sendMessageResponse);

//    SendMessageResponse sendMessageResponse = stub.sendMessage(SendMessageRequest.newBuilder()
//      .setRecipientUserId("XXX")
//      .setMessage("FEWE")
//      .build());
//    System.out.println(sendMessageResponse);
  }
}
