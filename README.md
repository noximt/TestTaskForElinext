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
* второй тест проверяет Provider на null, если есть binding и если его нет.
* третий тест проверяет на создание Singleton'ов для зависимостей класса.
* четвертый тест проверяет на создание Singleton'а для основного класса и его зависимостей.











