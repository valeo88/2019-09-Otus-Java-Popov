package ru.otus.hw15.messagesystem;

public enum MessageType {
  USER_DATA("UserData"),
  USER_DATA_COLLECTION("UserDataCollection");

  private final String value;

  MessageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
