package io.github.nfdz.dependencylocator;

public class MyDependency implements Dependency {

    public static class MyDependencyProvider implements DependencyProvider {

        public boolean created = false;
        public boolean initialized = false;
        public boolean destroyed = false;

        @Override
        public Dependency create() {
            created = true;
            return new MyDependency();
        }

        @Override
        public void initialize(Dependency dependency) {
            initialized = true;
        }

        @Override
        public void destroy(Dependency dependency) {
            destroyed = true;
        }
    }

}
