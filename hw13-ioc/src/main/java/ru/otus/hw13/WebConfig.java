package ru.otus.hw13;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import ru.otus.hw13.api.cache.UserCache;
import ru.otus.hw13.api.model.AddressDataSet;
import ru.otus.hw13.api.model.PhoneDataSet;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.repository.UserRepository;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.api.service.UserServiceCachedImpl;
import ru.otus.hw13.cachehw.UserCacheImpl;
import ru.otus.hw13.hibernate.HibernateUtils;
import ru.otus.hw13.hibernate.dao.UserRepositoryHibernate;
import ru.otus.hw13.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw13.web.service.UserAuthService;
import ru.otus.hw13.web.service.UserAuthServiceImpl;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "/WEB-INF/config/hibernate.cfg.xml";

    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
    }

    @Bean
    public SessionManagerHibernate sessionManager() {
        return new SessionManagerHibernate(sessionFactory());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryHibernate(sessionManager());
    }

    @Bean
    public UserCache userCache() {
        return new UserCacheImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceCachedImpl(userRepository(), userCache());
    }

    @Bean
    public UserAuthService userAuthService() {
        return new UserAuthServiceImpl(userRepository());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/static/");
    }
}
