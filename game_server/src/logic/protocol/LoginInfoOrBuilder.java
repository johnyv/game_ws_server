// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Protocol.proto

package logic.protocol;

public interface LoginInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:protocol.LoginInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <code>string pwd = 2;</code>
   * @return The pwd.
   */
  java.lang.String getPwd();
  /**
   * <code>string pwd = 2;</code>
   * @return The bytes for pwd.
   */
  com.google.protobuf.ByteString
      getPwdBytes();

  /**
   * <code>uint64 enterTime = 3;</code>
   * @return The enterTime.
   */
  long getEnterTime();
}
