package by.yauhenistaradubets.provider;

import by.yauhenistaradubets.injector.ConstructorWrapper;

import java.lang.reflect.Constructor;
import java.util.List;

public class ProviderDefinition {

    private Constructor<?> constructor;
    private List<ConstructorWrapper> dependencies;
    private boolean isSingleton;

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public List<ConstructorWrapper> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ConstructorWrapper> dependencies) {
        this.dependencies = dependencies;
    }
}
