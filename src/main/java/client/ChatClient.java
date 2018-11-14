package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import protos.*;

import java.util.Iterator;

public class ChatClient {
  public static void main(String[] args) {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
      .usePlaintext(true)
      .build();
    ChatGrpc.ChatBlockingStub stub = ChatGrpc.newBlockingStub(managedChannel);

    Iterator<Message> adqwdwwd = stub.receiveMessage(ReceiveMessageRequest.newBuilder()
      .setJwtToken("adqwdwwd")
      .build());
    while(adqwdwwd.hasNext()) {
      System.out.println(adqwdwwd.next());
    }

//    SendMessageResponse sendMessageResponse = stub.sendMessage(SendMessageRequest.newBuilder()
//      .setRecipientUserId("XXX")
//      .setMessage("FEWE")
//      .build());
//    System.out.println(sendMessageResponse);
  }
}
