package by.yauhenistaradubets.injector;

import java.lang.reflect.Constructor;

public class ConstructorWrapper {
    private boolean isSingleton;
    private Constructor<?> constructor;

    public ConstructorWrapper() {
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        this.isSingleton = singleton;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }
}
