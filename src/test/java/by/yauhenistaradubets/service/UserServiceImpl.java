package by.yauhenistaradubets.service;

import by.yauhenistaradubets.dao.AddressDao;
import by.yauhenistaradubets.dao.UserDao;
import by.yauhenistaradubets.injector.Inject;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private AddressDao addressDao;

    @Inject
    public UserServiceImpl(UserDao userDao, AddressDao addressDao) {
        this.userDao = userDao;
        this.addressDao = addressDao;
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "userDao=" + userDao +
                ", addressDao=" + addressDao +
                '}';
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AddressDao getAddressDao() {
        return addressDao;
    }

}
