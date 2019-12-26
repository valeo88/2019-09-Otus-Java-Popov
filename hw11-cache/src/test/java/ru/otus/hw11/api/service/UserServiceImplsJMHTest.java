package ru.otus.hw11.api.service;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.otus.hw11.api.model.AddressDataSet;
import ru.otus.hw11.api.model.PhoneDataSet;
import ru.otus.hw11.api.model.User;
import ru.otus.hw11.cachehw.UserCacheImpl;
import ru.otus.hw11.hibernate.HibernateUtils;
import ru.otus.hw11.hibernate.dao.UserDaoHibernate;
import ru.otus.hw11.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.concurrent.TimeUnit;

/** Сравнение скорости работы {@link UserServiceImpl} и {@link UserServiceCachedImpl}.*/
@State(value = Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UserServiceImplsJMHTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private int usersCount = 100;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(UserServiceImplsJMHTest.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }

    private UserService userService;
    private UserService cachedUserService;

    @Setup
    public void setup() throws Exception {
        var sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
        var sessionManager = new SessionManagerHibernate(sessionFactory);
        var userDao = new UserDaoHibernate(sessionManager);
        var userCache = new UserCacheImpl();

        userService = new UserServiceImpl(userDao);
        cachedUserService = new UserServiceCachedImpl(userDao, userCache);

        save(userService);
        save(cachedUserService);
    }

    @Benchmark
    public void userServiceImplGetTest() throws Exception {
        get(userService,1 );
    }

    @Benchmark
    public void userServiceCachedImplGetTest() {
        get(cachedUserService, usersCount);
    }

    @Benchmark
    public void userServiceImplSaveGetTest() throws Exception {
        save(userService);
        get(userService,usersCount * 2 );
    }

    @Benchmark
    public void userServiceCachedImplSaveGetTest() {
        save(cachedUserService);
        get(cachedUserService, usersCount * 2);
    }

    private void save(UserService userService) {
        for (int i = 0; i < usersCount; i++) {
            userService.saveUser(new User(String.format("Test %d", i)));
        }
    }

    private void get(UserService userService, int startIdx) {
        for (int i = startIdx; i < startIdx + usersCount + 1; i++) {
            userService.getUser(i);
        }
    }
}
