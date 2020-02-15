package ru.otus.hw15.api.handlers;

import ru.otus.hw15.api.dto.UserDTO;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.common.Serializers;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.RequestHandler;

import java.util.Optional;

public class CreateUserRequestHandler implements RequestHandler {
  private final UserService userService;

  public CreateUserRequestHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Optional<Message> handle(Message msg) {
    UserDTO data = Serializers.deserialize(msg.getPayload(), UserDTO.class);

    User newUser = new User(data.getName());
    newUser.setLogin(data.getLogin());
    newUser.setPassword(data.getPassword());
    newUser.setIsAdmin(data.getIsAdmin());

    long userId = userService.saveUser(newUser);

    Optional<User> userOptional = userService.getUser(userId);
    UserDTO result = null;
    if (userOptional.isPresent())
      result = new UserDTO(userOptional.get());

    return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(),
            MessageType.USER_DATA.getValue(), Serializers.serialize(result)));
  }
}
