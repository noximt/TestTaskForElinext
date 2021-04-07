package by.yauhenistaradubets;

import by.yauhenistaradubets.dao.AddressDao;
import by.yauhenistaradubets.dao.AddressDaoImpl;
import by.yauhenistaradubets.dao.UserDao;
import by.yauhenistaradubets.dao.UserDaoImpl;
import by.yauhenistaradubets.injector.Injector;
import by.yauhenistaradubets.injector.InjectorImpl;
import by.yauhenistaradubets.provider.Provider;
import by.yauhenistaradubets.service.UserService;
import by.yauhenistaradubets.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    private Injector injector;

    @Before
    public void setUp() {
        this.injector = new InjectorImpl();
    }

    @Test
    public void testExistingBinding() {
        this.injector.bind(UserDao.class, UserDaoImpl.class);
        this.injector.bind(AddressDao.class, AddressDaoImpl.class);
        this.injector.bind(UserService.class, UserServiceImpl.class);
        Provider<UserService> provider = this.injector.getProvider(UserService.class);
        UserService instance = provider.getInstance();
        UserServiceImpl userService = (UserServiceImpl) instance;


        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(UserServiceImpl.class, provider.getInstance().getClass());
        assertNotNull(userService.getUserDao());
        assertNotNull(userService.getAddressDao());
        assertSame(userService.getUserDao().getClass(), UserDaoImpl.class);
        assertSame(userService.getAddressDao().getClass(), AddressDaoImpl.class);
    }

    @Test
    public void testWithoutBindingAndWithBinding() {
        Provider<UserDao> provider = this.injector.getProvider(UserDao.class);
        assertNull(provider);
        this.injector.bind(AddressDao.class, AddressDaoImpl.class);
        Provider<AddressDao> provider1 = this.injector.getProvider(AddressDao.class);
        assertNotNull(provider1);
    }

    @Test
    public void testSingletonDependency() {
        this.injector.bindSingleton(UserDao.class, UserDaoImpl.class);
        this.injector.bind(AddressDao.class, AddressDaoImpl.class);
        this.injector.bind(UserService.class, UserServiceImpl.class);
        Provider<UserService> provider = this.injector.getProvider(UserService.class);
        Provider<UserService> provider1 = this.injector.getProvider(UserService.class);
        UserService instance1 = provider1.getInstance();
        UserService instance = provider.getInstance();
        UserServiceImpl userService1 = (UserServiceImpl) instance1;
        UserServiceImpl userService = (UserServiceImpl) instance;


        assertNotEquals(provider, provider1);
        assertSame(userService.getUserDao(), userService1.getUserDao());
        assertNotEquals(userService.getAddressDao(), userService1.getAddressDao());
        assertNotEquals(userService, userService1);
    }

    @Test
    public void testSingletonMainClass() {
        this.injector.bind(UserDao.class, UserDaoImpl.class);
        this.injector.bind(AddressDao.class, AddressDaoImpl.class);
        this.injector.bindSingleton(UserService.class, UserServiceImpl.class);
        Provider<UserService> provider = this.injector.getProvider(UserService.class);
        Provider<UserService> provider1 = this.injector.getProvider(UserService.class);
        UserService instance1 = provider1.getInstance();
        UserService instance = provider.getInstance();
        UserServiceImpl userService1 = (UserServiceImpl) instance1;
        UserServiceImpl userService = (UserServiceImpl) instance;


        assertNotNull(provider);
        assertNotNull(provider1);
        assertNotNull(instance);
        assertNotNull(instance1);
        assertNotNull(userService);
        assertNotNull(userService1);
        assertSame(provider, provider1);
        assertSame(instance1, instance);
        assertSame(userService, userService1);
    }
}
