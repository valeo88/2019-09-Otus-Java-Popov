package ru.otus.hw15;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw15.api.handlers.GetUserDataRequestHandler;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.messagesystem.*;
import ru.otus.hw15.web.handlers.GetUserDataResponseHandler;
import ru.otus.hw15.web.service.FrontendService;

@Configuration
@ComponentScan
public class MessagingConfig {
    public static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String BACKEND_SERVICE_CLIENT_NAME = "backendService";

    private final ApplicationContext applicationContext;

    public MessagingConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public MsClient backendMsClient() {
        UserService userService = applicationContext.getBean(UserService.class);
        MessageSystem messageSystem = messageSystem();

        MsClient client = new MsClientImpl(BACKEND_SERVICE_CLIENT_NAME, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(userService));
        messageSystem.addClient(client);

        return client;
    }

    @Bean
    public MsClient frontendMsClient() {
        FrontendService frontendService = applicationContext.getBean(FrontendService.class);
        MessageSystem messageSystem = messageSystem();

        MsClient client = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        client.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        messageSystem.addClient(client);

        return client;
    }

}
