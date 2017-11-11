package io.github.nfdz.dependencylocator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyLocator {

    private static final String NO_PROVIDER_ERROR = "There is no provider available for given dependency class";

    private static final Map<Class, DependencyProvider> providersMap = new ConcurrentHashMap<>();
    private static final Map<Class,Dependency> singletonDependenciesMap = new ConcurrentHashMap<>();
    private static final Map<Class,AtomicInteger> singletonDependenciesCounterMap = new ConcurrentHashMap<>();
    private static final ReentrantLock singletonLock = new ReentrantLock();

    public static void provide(Class<? extends Dependency> dependencyClass, DependencyProvider provider) {
        providersMap.put(dependencyClass, provider);
    }

    public static Dependency locate(Class<? extends Dependency> dependencyClass) {
        if (isSingleton(dependencyClass)) {
            return locateSingletonDependency(dependencyClass);
        } else {
            return locateDependency(dependencyClass);
        }
    }

    private static boolean isSingleton(Class<? extends Dependency> dependencyClass) {
        return SingletonDependency.class.isAssignableFrom(dependencyClass);
    }

    private static Dependency locateSingletonDependency(Class<? extends Dependency> dependencyClass) {
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

    private static Dependency locateDependency(Class<? extends Dependency> dependencyClass) {
        DependencyProvider provider = providersMap.get(dependencyClass);
        if (provider == null) {
            throw new DependencyLocatorException(NO_PROVIDER_ERROR);
        } else {
            Dependency dependency = provider.create();
            provider.initialize(dependency);
            return dependency;
        }
    }

    public static void release(Dependency dependency) {
        Class<? extends Dependency> dependencyClass = dependency.getClass();
        if (isSingleton(dependencyClass)) {
            releaseSingletonDependency(dependency, dependencyClass);
        } else {
            releaseDependency(dependency, dependencyClass);
        }
    }

    private static void releaseSingletonDependency(Dependency dependency, Class<? extends Dependency> dependencyClass) {
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

    private static void releaseDependency(Dependency dependency, Class<? extends Dependency> dependencyClass) {
        DependencyProvider provider = providersMap.get(dependencyClass);
        provider.destroy(dependency);
    }
}
