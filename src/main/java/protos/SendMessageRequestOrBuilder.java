// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package protos;

public interface SendMessageRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:protos.SendMessageRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string recipientUserId = 1;</code>
   */
  java.lang.String getRecipientUserId();
  /**
   * <code>string recipientUserId = 1;</code>
   */
  com.google.protobuf.ByteString
      getRecipientUserIdBytes();

  /**
   * <code>string message = 2;</code>
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>string jwtToken = 3;</code>
   */
  java.lang.String getJwtToken();
  /**
   * <code>string jwtToken = 3;</code>
   */
  com.google.protobuf.ByteString
      getJwtTokenBytes();
}