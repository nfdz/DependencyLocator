package io.github.nfdz.dependencylocator;

/**
 * Dummy implementation of a Dependency.
 */
public class MyDependency implements Dependency {

    public String tag;

    public MyDependency(String tag) {
        this.tag = tag;
    }

    public static class MyDependencyProvider implements DependencyProvider {

        public boolean created = false;
        public boolean destroyed = false;
        public int createCounter = 0;
        public int destroyCounter = 0;
        public String tag = "";

        public MyDependencyProvider() {
        }

        public MyDependencyProvider(String tag) {
            this.tag = tag;
        }

        @Override
        public Dependency create() {
            created = true;
            createCounter++;
            return new MyDependency(tag);
        }

        @Override
        public void destroy(Dependency dependency) {
            destroyed = true;
            destroyCounter++;
        }
    }

}
