package by.yauhenistaradubets.provider;

import by.yauhenistaradubets.injector.ConstructorWrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProviderImpl<T> implements Provider<T> {

    private T singleton;
    private final boolean isSingleton;
    private final Constructor<T> constructor;
    private Map<Constructor<?>, Object> dependenciesSingleton;
    private List<ConstructorWrapper> allDependencies;

    public ProviderImpl(Constructor<T> constructor, Map<Constructor<?>, Object> dependencies, List<ConstructorWrapper> allDependencies, boolean isSingleton) {
        this.constructor = constructor;
        this.dependenciesSingleton = dependencies;
        this.allDependencies = allDependencies;
        this.isSingleton = isSingleton;
    }

    public ProviderImpl(Constructor<T> constructor, boolean isSingleton) {
        this.isSingleton = isSingleton;
        this.constructor = constructor;
    }

    @Override
    public T getInstance() {
        List<Object> objectsDependencies = new ArrayList<>();
        if (this.allDependencies == null) {
            try {
                return this.constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for (ConstructorWrapper dependency : this.allDependencies) {
            if (dependency == null) {
                objectsDependencies.add(null);
                continue;
            }
            Constructor<?> constructor = dependency.getConstructor();
            if (this.dependenciesSingleton.containsKey(constructor)) {
                objectsDependencies.add(this.dependenciesSingleton.get(constructor));
            } else {
                try {
                    Object o = constructor.newInstance();
                    objectsDependencies.add(o);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (this.isSingleton) {
                if (this.singleton == null) {
                    this.singleton = constructor.newInstance(objectsDependencies.toArray());
                    return this.singleton;
                } else {
                    return this.singleton;
                }
            } else {
                return this.constructor.newInstance(objectsDependencies.toArray());
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
