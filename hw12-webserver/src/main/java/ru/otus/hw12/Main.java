package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.hw12.api.dao.UserDao;
import ru.otus.hw12.api.model.AddressDataSet;
import ru.otus.hw12.api.model.PhoneDataSet;
import ru.otus.hw12.api.model.User;
import ru.otus.hw12.api.service.UserService;
import ru.otus.hw12.api.service.UserServiceImpl;
import ru.otus.hw12.hibernate.HibernateUtils;
import ru.otus.hw12.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12.web.server.UsersWebServer;
import ru.otus.hw12.web.server.UsersWebServerImpl;
import ru.otus.hw12.web.service.*;

import static ru.otus.hw12.web.server.SecurityType.FILTER_BASED;

public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        // hibernate
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        // DAO and business layer services
        UserDao userDao = new UserDaoHibernate(sessionManager);
        UserService userService = new UserServiceImpl(userDao);

        // Web services
        UserAuthService userAuthServiceForFilterBasedSecurity = new UserAuthServiceImpl(userService);
        LoginService loginServiceForBasicSecurity = new InMemoryLoginServiceImpl(userService);

        // Other
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                FILTER_BASED,
                userAuthServiceForFilterBasedSecurity,
                loginServiceForBasicSecurity,
                userService,
                gson,
                templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

}
