package by.yauhenistaradubets.injector;

import by.yauhenistaradubets.exceptions.BindingNotFoundException;
import by.yauhenistaradubets.exceptions.ConstructorNotFoundException;
import by.yauhenistaradubets.exceptions.TooManyConstructorsException;
import by.yauhenistaradubets.provider.Provider;
import by.yauhenistaradubets.provider.ProviderDefinition;
import by.yauhenistaradubets.provider.ProviderImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InjectorImpl implements Injector {

    private final Map<Class<?>, ProviderDefinition> map = new ConcurrentHashMap<>();
    private final Map<Constructor<?>, Object> mapSingletons = new ConcurrentHashMap<>();
    private Provider<?> mainSingleton;

    @Override
    @SuppressWarnings("unchecked")
    public <T> Provider<T> getProvider(Class<T> type) {
        ProviderDefinition providerDefinition = this.map.get(type);
        if (providerDefinition == null) {
            return null;
        }
        if (providerDefinition.getDependencies() == null) {
            Provider<?> provider = new ProviderImpl<>(providerDefinition.getConstructor(), providerDefinition.isSingleton());
            return (Provider<T>) provider;
        }
        dependenciesSingletonInit(providerDefinition);
        Provider<?> mainSingleton1 = mainSingletonInit(providerDefinition);
        if (mainSingleton1 != null) return (Provider<T>) mainSingleton1;
        Provider<?> provider = new ProviderImpl<>(providerDefinition.getConstructor(), this.mapSingletons, providerDefinition.getDependencies(), providerDefinition.isSingleton());
        return (Provider<T>) provider;
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        bindWithAllDependencies(intf, impl, false);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        bindWithAllDependencies(intf, impl, true);
    }

    private void dependenciesSingletonInit(ProviderDefinition providerDefinition) {
        for (ConstructorWrapper dependency : providerDefinition.getDependencies()) {
            if (dependency == null) {
                continue;
            }
            try {
                if (dependency.isSingleton()) {
                    Constructor<?> constructor = dependency.getConstructor();
                    this.mapSingletons.put(constructor, constructor.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private Provider<?> mainSingletonInit(ProviderDefinition providerDefinition) {
        if (providerDefinition.isSingleton()) {
            if (this.mainSingleton == null) {
                this.mainSingleton = new ProviderImpl<>(providerDefinition.getConstructor(), this.mapSingletons, providerDefinition.getDependencies(), providerDefinition.isSingleton());
                return this.mainSingleton;
            } else {
                return this.mainSingleton;
            }
        }
        return null;
    }

    private <T> void bindWithAllDependencies(Class<T> intf, Class<? extends T> impl, boolean isSingleton) {
        Constructor<?>[] declaredConstructors = impl.getDeclaredConstructors();
        int count = 0;
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (declaredConstructor.isAnnotationPresent(Inject.class)) {
                if (count > 0) {
                    throw new TooManyConstructorsException();
                }
                ProviderDefinition objectProviderDefinition = new ProviderDefinition();
                objectProviderDefinition.setSingleton(isSingleton);
                objectProviderDefinition.setConstructor(declaredConstructor);
                Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
                List<ConstructorWrapper> constructors = new ArrayList<>();
                for (Class<?> parameterType : parameterTypes) {
                    ProviderDefinition providerDefinition = this.map.get(parameterType);
                    if (providerDefinition != null) {
                        ConstructorWrapper constructorWrapper = new ConstructorWrapper();
                        constructorWrapper.setSingleton(providerDefinition.isSingleton());
                        constructorWrapper.setConstructor(providerDefinition.getConstructor());
                        constructors.add(constructorWrapper);
                    } else {
                        throw new BindingNotFoundException();
                    }
                }
                objectProviderDefinition.setDependencies(constructors);
                count++;
                this.map.put(intf, objectProviderDefinition);
            } else {
                ProviderDefinition objectProviderDefinition = new ProviderDefinition();
                objectProviderDefinition.setSingleton(isSingleton);
                Constructor<?>[] declaredConstructors1 = impl.getDeclaredConstructors();
                for (Constructor<?> constructor : declaredConstructors1) {
                    if (constructor.getParameterCount() == 0) {
                        objectProviderDefinition.setConstructor(constructor);
                        this.map.put(intf, objectProviderDefinition);
                    } else {
                        throw new ConstructorNotFoundException();
                    }
                }
            }
        }
    }
}
