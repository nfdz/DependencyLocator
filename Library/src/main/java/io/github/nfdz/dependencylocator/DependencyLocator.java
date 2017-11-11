package io.github.nfdz.dependencylocator;

public class DependencyLocator {

    private static final DependencyLocatorImpl INSTANCE = new DependencyLocatorImpl();

    public static void provide(Class<? extends Dependency> dependencyClass, DependencyProvider provider) {
        INSTANCE.provide(dependencyClass, provider);
    }

    public static Dependency locate(Class<? extends Dependency> dependencyClass) {
        return INSTANCE.locate(dependencyClass);
    }

    public static void release(Dependency dependency) {
        INSTANCE.release(dependency);
    }
}