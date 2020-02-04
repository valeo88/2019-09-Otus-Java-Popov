package ru.otus.hw15.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.hw15.MessagingConfig;
import ru.otus.hw15.messagesystem.Message;
import ru.otus.hw15.messagesystem.MessageType;
import ru.otus.hw15.messagesystem.MsClient;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final String backendServiceClientName = MessagingConfig.BACKEND_SERVICE_CLIENT_NAME;

  private MsClient msClient;

  @Override
  public void getUserData(long userId, Consumer<String> dataConsumer) {
    Message outMsg = msClient.produceMessage(backendServiceClientName, userId, MessageType.USER_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }

  @Qualifier("frontendMsClient")
  public void setMsClient(MsClient msClient) {
    this.msClient = msClient;
  }
}
