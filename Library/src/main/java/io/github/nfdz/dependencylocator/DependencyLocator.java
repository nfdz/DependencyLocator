package io.github.nfdz.dependencylocator;

/**
 * It is the default DependencyLocator.
 */
public class DependencyLocator {

    private static final DependencyLocatorImpl INSTANCE = new DependencyLocatorImpl();

    /**
     * Set a DependencyProvider that manages creation/destruction of given Dependency class.
     * It is possible override a DependencyProvider set before but if it has already used it
     * will throw a DependencyLocatorException.
     * @param dependencyClass
     * @param provider
     * @throws DependencyLocatorException
     */
    public static void provide(Class<? extends Dependency> dependencyClass, DependencyProvider provider) {
        INSTANCE.provide(dependencyClass, provider);
    }

    /**
     * Locate an instance of given Dependency class. If DependencyLocator has no providers for
     * given Dependency class, it will throw DependencyLocatorException.
     * @param dependencyClass
     * @return Dependency
     * @throws DependencyLocatorException
     */
    public static Dependency locate(Class<? extends Dependency> dependencyClass) {
        return INSTANCE.locate(dependencyClass);
    }

    /**
     * Release the instance of the Dependency. You should not retains a reference or use it anymore
     * because it could be destroyed by its provider.
     * @param dependency Dependency
     */
    public static void release(Dependency dependency) {
        INSTANCE.release(dependency);
    }
}