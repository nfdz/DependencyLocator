package io.github.nfdz.dependencylocator;

/**
 * This interface must be implemented by the classes that knows how to manage the lifetime of the dependency.
 */
public interface DependencyProvider {

    /**
     * Create an instance of the Dependency.
     * @return Dependency
     */
    Dependency create();

    /**
     * Destroy an instance of the Dependency.
     * @param dependency
     */
    void destroy(Dependency dependency);

}
