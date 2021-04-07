package by.yauhenistaradubets.injector;

import by.yauhenistaradubets.provider.Provider;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type);
    <T> void bind(Class<T> intf, Class<? extends T> impl);
    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl); //регистрация синглтон класса 
}
