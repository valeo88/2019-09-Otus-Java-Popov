package ru.otus.hw15.api.handlers;

import ru.otus.hw15.api.dto.UserDTO;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.common.Serializers;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetUserDataCollectionRequestHandler implements RequestHandler {
  private final UserService userService;

  public GetUserDataCollectionRequestHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    List<UserDTO> data = userService.getAll().stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
            MessageType.USER_DATA_COLLECTION.getValue(), Serializers.serialize(data)));
  }
}
