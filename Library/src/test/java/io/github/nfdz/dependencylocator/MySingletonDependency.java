package io.github.nfdz.dependencylocator;

/**
 * Dummy implementation of a SingletonDependency.
 */
public class MySingletonDependency implements SingletonDependency {

    public static class MySingletonDependencyProvider implements DependencyProvider {

        public int createCounter = 0;
        public int destroyCounter = 0;

        @Override
        public Dependency create() {
            createCounter++;
            return new MySingletonDependency();
        }

        @Override
        public void destroy(Dependency dependency) {
            destroyCounter++;
        }
    }

}
