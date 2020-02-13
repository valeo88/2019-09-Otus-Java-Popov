package ru.otus.hw15;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw15.api.handlers.CreateUserRequestHandler;
import ru.otus.hw15.api.handlers.GetUserDataCollectionRequestHandler;
import ru.otus.hw15.api.handlers.GetUserDataRequestHandler;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.messagesystem.*;
import ru.otus.hw15.web.handlers.GetUserDataCollectionResponseHandler;
import ru.otus.hw15.web.handlers.GetUserDataResponseHandler;
import ru.otus.hw15.web.service.FrontendService;

@Configuration
public class MessagingConfig {

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public MsClient backendMsClient(UserService userService,
                                    MessageSystem messageSystem,
                                    @Value("${backendServiceClientName}") String backendServiceClientName) {
        MsClient client = new MsClientImpl(backendServiceClientName, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(userService));
        client.addHandler(MessageType.USER_DATA_COLLECTION, new GetUserDataCollectionRequestHandler(userService));
        client.addHandler(MessageType.NEW_USER_DATA, new CreateUserRequestHandler(userService));
        messageSystem.addClient(client);

        return client;
    }

    @Bean
    public MsClient frontendMsClient(FrontendService frontendService,
                                     MessageSystem messageSystem,
                                     @Value("${frontendServiceClientName}") String frontendServiceClientName) {
        MsClient client = new MsClientImpl(frontendServiceClientName, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        client.addHandler(MessageType.USER_DATA_COLLECTION, new GetUserDataCollectionResponseHandler(frontendService));
        messageSystem.addClient(client);

        return client;
    }

}
