package io.github.nfdz.dependencylocator;

/**
 * This interface must be implemented by classes that want to provide a Dependency instance for each time that
 * someone locates it with DependencyLocator. That instance will be destroyed when user releases it.
 */
public interface Dependency {
}
