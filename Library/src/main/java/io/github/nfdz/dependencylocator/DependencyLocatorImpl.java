package io.github.nfdz.dependencylocator;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyLocatorImpl {

    private static final String NO_PROVIDER_ERROR = "There is no provider available for given dependency class";
    private static final String PROVIDER_USED_ERROR = "The dependency provider that you want to override has already been used";

    private final Map<Class, DependencyProvider> providersMap = new ConcurrentHashMap<>();
    private final Map<Class,Dependency> singletonDependenciesMap = new ConcurrentHashMap<>();
    private final Map<Class,AtomicInteger> singletonDependenciesCounterMap = new ConcurrentHashMap<>();
    private final ReentrantLock singletonLock = new ReentrantLock();
    private final Set<Class> usedProviders = new CopyOnWriteArraySet<>();

    public void provide(Class<? extends Dependency> dependencyClass, DependencyProvider provider) {
        // TODO Use other approach to know if a provider was used because this one could has leaks
        if (usedProviders.contains(dependencyClass)) {
            throw new DependencyLocatorException(PROVIDER_USED_ERROR);
        }
        providersMap.put(dependencyClass, provider);
    }

    public Dependency locate(Class<? extends Dependency> dependencyClass) {
        usedProviders.add(dependencyClass);
        if (isSingleton(dependencyClass)) {
            return locateSingletonDependency(dependencyClass);
        } else {
            return locateDependency(dependencyClass);
        }
    }

    private boolean isSingleton(Class<? extends Dependency> dependencyClass) {
        return SingletonDependency.class.isAssignableFrom(dependencyClass);
    }

    private Dependency locateSingletonDependency(Class<? extends Dependency> dependencyClass) {
        try {
            singletonLock.lock();
            Dependency dependency = singletonDependenciesMap.get(dependencyClass);
            if (dependency == null) {
                DependencyProvider provider = providersMap.get(dependencyClass);
                if (provider == null) {
                    throw new DependencyLocatorException(NO_PROVIDER_ERROR);
                } else {
                    dependency = provider.create();
                    provider.initialize(dependency);
                    singletonDependenciesMap.put(dependencyClass, dependency);
                    singletonDependenciesCounterMap.put(dependencyClass, new AtomicInteger());
                }
            }
            singletonDependenciesCounterMap.get(dependencyClass).incrementAndGet();
            return dependency;
        } finally {
            singletonLock.unlock();
        }
    }

    private Dependency locateDependency(Class<? extends Dependency> dependencyClass) {
        DependencyProvider provider = providersMap.get(dependencyClass);
        if (provider == null) {
            throw new DependencyLocatorException(NO_PROVIDER_ERROR);
        } else {
            Dependency dependency = provider.create();
            provider.initialize(dependency);
            return dependency;
        }
    }

    public void release(Dependency dependency) {
        Class<? extends Dependency> dependencyClass = dependency.getClass();
        if (isSingleton(dependencyClass)) {
            releaseSingletonDependency(dependency, dependencyClass);
        } else {
            releaseDependency(dependency, dependencyClass);
        }
    }

    private void releaseSingletonDependency(Dependency dependency, Class<? extends Dependency> dependencyClass) {
        try {
            singletonLock.lock();
            AtomicInteger singletonCounter = singletonDependenciesCounterMap.get(dependencyClass);
            if (singletonCounter.decrementAndGet() == 0) {
                singletonDependenciesMap.remove(dependencyClass);
                singletonDependenciesCounterMap.remove(dependencyClass);
                DependencyProvider provider = providersMap.get(dependencyClass);
                provider.destroy(dependency);
            }
        } finally {
            singletonLock.unlock();
        }
    }

    private void releaseDependency(Dependency dependency, Class<? extends Dependency> dependencyClass) {
        DependencyProvider provider = providersMap.get(dependencyClass);
        provider.destroy(dependency);
    }

}
