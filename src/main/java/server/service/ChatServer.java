package server.service;

import io.grpc.stub.StreamObserver;
import protos.*;
import redis.clients.jedis.Jedis;
import server.exceptions.AppException;

import static server.exceptions.ErrorCode.InvalidCredentials;
import static server.models.Status.FAILED;
import static server.models.Status.SUCCESS;
import static server.service.ResponseHelper.sendFailureLoginResponse;
import static server.service.ResponseHelper.sendSuccessfulLoginResponse;

public class ChatServer extends ChatGrpc.ChatImplBase {

  private AuthenticationService authenticationService;
  private MessageService messageService;

  public ChatServer(Jedis jedis) {
    authenticationService = new AuthenticationService(jedis);
    messageService = new MessageService(jedis);
  }

  @Override
  public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
    try {
      String token = authenticationService.authenticate(request.getUserId(), request.getPassword());
      sendSuccessfulLoginResponse(responseObserver, token);
    } catch (AppException ex) {
      if (ex.errorCode() == InvalidCredentials) {
        sendFailureLoginResponse(responseObserver);
      } else {
        throw ex;
      }
    }
  }

  @Override
  public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
    String fromUserId = authenticationService.validateUser(request.getJwtToken());
    String toUserId = request.getRecipientUserId();
    String message = request.getMessage();

    boolean isSent = messageService.sendMessage(fromUserId, toUserId, message);

    responseObserver.onNext(SendMessageResponse.newBuilder()
      .setStatus(isSent ? SUCCESS.name() : FAILED.name())
      .build());
    responseObserver.onCompleted();
  }

  @Override
  public void receiveMessage(ReceiveMessageRequest request, StreamObserver<Message> responseObserver) {
    String userId = authenticationService.validateUser(request.getJwtToken());
    messageService.openReceiverStream(userId, responseObserver);
  }
}
