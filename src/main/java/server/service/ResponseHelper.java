package server.service;

import io.grpc.stub.StreamObserver;
import protos.LoginResponse;

import static server.models.Status.FAILED;
import static server.models.Status.SUCCESS;

public class ResponseHelper {

  public static void sendSuccessfulLoginResponse(StreamObserver<LoginResponse> stream, String token) {
    stream.onNext(LoginResponse.newBuilder()
      .setJwtToken(token)
      .setStatus(SUCCESS.name())
      .build());
    stream.onCompleted();
  }

  public static void sendFailureLoginResponse(StreamObserver<LoginResponse> stream) {
    stream.onNext(LoginResponse.newBuilder()
      .setStatus(FAILED.name())
      .build());
    stream.onCompleted();
  }
}
