package ru.otus.hw15.api.handlers;

import ru.otus.hw15.api.dto.UserDTO;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.common.Serializers;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.Optional;

public class GetUserDataRequestHandler implements RequestHandler {
  private final UserService userService;

  public GetUserDataRequestHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    long id = Serializers.deserialize(msg.getPayload(), Long.class);
    Optional<User> userOptional = userService.getUser(id);
    UserDTO data = null;
    if (userOptional.isPresent())
      data = new UserDTO(userOptional.get());
    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
            MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
  }
}
