package io.github.nfdz.dependencylocator;

/**
 * This interface must be implemented by classes that want to provide the same instance for all users.
 * That does not means that it will be created and initialized once because it could be destroyed when
 * all users release the dependency. However, if you want to use as a permanent singleton, you just
 * have to provide the same instance in its DependencyProvider.
 */
public interface SingletonDependency extends Dependency {
}
