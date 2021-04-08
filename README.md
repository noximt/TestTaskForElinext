# Тестовое задание для Elinext

### Установка

* Для сборки требуется установить JDK и Maven
* Подробную пошаговую инструкцию по установке для различных операционных систем можно найти
  на https://www.baeldung.com/install-maven-on-windows-linux-mac

### Cборка

Сборка происходит с помощью команды mvn package

### Использование

Пример класса с зависимостями:

```java
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private AddressDao addressDao;

    @Inject
    public UserServiceImpl(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }
}
```

Для осуществления binding'а в конструктор класса имплементации добавляется аннотация @Inject как показано выше. Затем
нужно произвести binding всех зависимостей класса и сам класс с помощью метода bind():

```java
    Injector injector = new InjectorImpl();
    injector.bind(UserDao.class,UserDaoImpl.class);
    injector.bind(AddressDao.class,AddressDaoImpl.class);
    injector.bind(UserService.class,UserServiceImpl.class);
```
Для создание Singleton'ов необходимо использовать метод bindSingleton() вместо bind().
Для получения Provider'а необходимого класса используется метод getProvider(). Затем предоставление instance производится у Provider'а методом getInstace():
```java
    Provider<UserService> provider = this.injector.getProvider(UserService.class);
    UserService instance = provider.getInstance();
```
### Тесты

Для сборки написано 4 теста, покрывающие следующие ситуации:

* первый тест проверяет класс и его зависимости на все binding'и, принадлежность к одному классу и на создание instance.
```java
    @Test
    public void testExistingBinding() {
        injector.bind(UserDao.class, UserDaoImpl.class);
        injector.bind(AddressDao.class, AddressDaoImpl.class);
        injector.bind(UserService.class, UserServiceImpl.class);
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
```
* второй тест проверяет Provider на null, если есть binding и если его нет.
```java
    @Test
    public void testWithoutBindingAndWithBinding() {
        Provider<UserDao> provider = this.injector.getProvider(UserDao.class);
        assertNull(provider);
        this.injector.bind(AddressDao.class, AddressDaoImpl.class);
        Provider<AddressDao> provider1 = this.injector.getProvider(AddressDao.class);
        assertNotNull(provider1);
    }
```  
* третий тест проверяет на создание Singleton'ов для зависимостей класса.
```java
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
```  
* четвертый тест проверяет на создание Singleton'а для основного класса и его зависимостей.
```java
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
```










