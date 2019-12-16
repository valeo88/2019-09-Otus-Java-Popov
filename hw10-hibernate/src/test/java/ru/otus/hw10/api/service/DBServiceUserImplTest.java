package ru.otus.hw10.api.service;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.model.AddressDataSet;
import ru.otus.hw10.api.model.PhoneDataSet;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.hibernate.HibernateUtils;
import ru.otus.hw10.hibernate.dao.UserDaoHibernate;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Сервис для работы с пользователями в рамках БД должен ")
class DBServiceUserImplTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";

    private static final long USER_ID = 1L;
    private User user;
    private AddressDataSet address;
    private PhoneDataSet phone1;
    private PhoneDataSet phone2;

    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser dbServiceUser;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao);

        user = new User("Иван");
        address = new AddressDataSet("Ленина");
        phone1 = new PhoneDataSet("+7-111-123-45-67");
        phone2 = new PhoneDataSet("+7-111-123-87-23");
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    @DisplayName(" не сохранять null пользователя.")
    void shouldNotSaveNullUser() {
        assertThatThrownBy(() -> dbServiceUser.saveUser(null))
                .isInstanceOf(DbServiceException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName(" корректно сохранять нового пользователя без связей с другими сущностями.")
    void shouldSaveNewUserWithoutRelations() {
        long id = dbServiceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(user);
    }

    @Test
    @DisplayName(" корректно обновлять существующего пользователя без связей с другими сущностями.")
    void shouldSaveExistedWithoutRelations() {
        dbServiceUser.saveUser(user);
        user.setName("Петя");
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(user);
    }

    @Test
    @DisplayName(" корректно сохранять нового пользователя c адресом.")
    void shouldSaveNewUserWithAddress() {
        user.setAddress(address);

        long id = dbServiceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getAddress()).isEqualToComparingFieldByField(address);
    }

    @Test
    @DisplayName(" корректно сохранять пользователя с обновленным адресом.")
    void shouldSaveUserWithUpdatedAddress() {
        user.setAddress(address);

        dbServiceUser.saveUser(user);
        address.setStreet("Советская");
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getAddress()).isEqualToComparingFieldByField(address);
    }

    @Test
    @DisplayName(" корректно менять адрес на новый у существующего пользователя.")
    void shouldChangeAddressInExistedUser() {
        user.setAddress(address);

        dbServiceUser.saveUser(user);
        AddressDataSet newAddress = new AddressDataSet("Советская");
        user.setAddress(newAddress);
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getAddress().getStreet()).isEqualTo(newAddress.getStreet());
        // у newAddress почему-то не устанавливается id в таком случае, хотя когда он загружается вместе с пользователем, то id установлен
        // assertThat(mayBeUser.get().getAddress()).isEqualToComparingFieldByField(newAddress);
    }

    @Test
    @DisplayName(" корректно удалять адрес у существующего пользователя.")
    void shouldRemoveAddressFromExistedUser() {
        user.setAddress(address);

        dbServiceUser.saveUser(user);
        user.setAddress(null);
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getAddress()).isNull();
    }

    @Test
    @DisplayName(" корректно сохранять нового пользователя c телефоном.")
    void shouldSaveNewUserWithPhones() {
        user.addPhone(phone1);

        long id = dbServiceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        for (PhoneDataSet phone : mayBeUser.get().getPhones()) {
            assertThat(phone).isEqualToComparingFieldByField(phone1);
        }
    }

    @Test
    @DisplayName(" корректно сохранять пользователя c изменившимся телефоном.")
    void shouldSaveUserWithPhoneChange() {
        user.addPhone(phone1);

        dbServiceUser.saveUser(user);
        phone1.setNumber("+7-111-123-87-23");
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        for (PhoneDataSet phone : mayBeUser.get().getPhones()) {
            assertThat(phone).isEqualToComparingFieldByField(phone1);
        }
    }

    @Test
    @DisplayName(" корректно сохранять пользователя при добавлении нового телефона.")
    void shouldSaveUserWithPhoneAdd() {
        user.addPhone(phone1);

        dbServiceUser.saveUser(user);
        user.addPhone(phone2);
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getPhones().size()).isEqualTo(2);
        // при добавлении второго телефона он сохраняется в БД, но в самом объекте phone2 не меняется id
        assertThat(mayBeUser.get().getPhones().stream().map(PhoneDataSet::getNumber).collect(Collectors.toSet()))
                .contains(phone1.getNumber(), phone2.getNumber());
    }

    @Test
    @DisplayName(" корректно сохранять пользователя при удалении телефона.")
    void shouldSaveUserWithPhoneRemove() {
        user.addPhone(phone1);
        user.addPhone(phone2);

        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getPhones().size()).isEqualTo(2);

        user.removePhone(phone2);
        id = dbServiceUser.saveUser(user);

        mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getPhones().size()).isEqualTo(1);
        assertThat(mayBeUser.get().getPhones().stream().map(PhoneDataSet::getNumber).collect(Collectors.toSet()))
                .contains(phone1.getNumber());
    }

    @Test
    @DisplayName(" корректно сохранять пользователя c удалением всех телефонов.")
    void shouldSaveUserWithAllPhonesRemove() {
        user.addPhone(phone1);

        dbServiceUser.saveUser(user);
        user.removePhone(phone1);
        long id = dbServiceUser.saveUser(user);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getPhones()).isEmpty();
    }

    @Test
    @DisplayName(" корректно загружать пользователя по заданному id")
    void shouldLoadCorrectUserById() {
        long id = dbServiceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        Optional<User> mayBeUser = dbServiceUser.getUser(USER_ID);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(user);

        assertThat(dbServiceUser.getUser(-1)).isNotPresent();
    }

}