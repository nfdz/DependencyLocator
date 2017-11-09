package io.github.nfdz.dependencylocator;

public class Main {

    public static void main(String[] args) {

        DependencyLocator.provide(MyDependency1.class, new MyDependency1.MyDependency1Provider());
        DependencyLocator.locate(MyDependency1.class);
        DependencyLocator.locate(MyDependency1.class);
        Dependency dep1 = DependencyLocator.locate(MyDependency1.class);

        DependencyLocator.provide(MyDependency2.class, new MyDependency2.MyDependency2Provider());
        DependencyLocator.locate(MyDependency2.class);
        DependencyLocator.locate(MyDependency2.class);
        DependencyLocator.locate(MyDependency2.class);
        DependencyLocator.locate(MyDependency2.class);
        Dependency dep2 = DependencyLocator.locate(MyDependency2.class);
        DependencyLocator.release(dep2);
        DependencyLocator.release(dep2);
        DependencyLocator.release(dep2);
        DependencyLocator.release(dep2);
        DependencyLocator.release(dep2);

        DependencyLocator.release(dep1);
    }

    public static class MyDependency1 implements Dependency {

        public static class MyDependency1Provider implements DependencyProvider {
            @Override
            public Dependency create() {
                System.out.println("MyDependency1Provider > create");
                return new MyDependency1();
            }

            @Override
            public void initialize(Dependency dependency) {
                System.out.println("MyDependency1Provider > initialize");
            }

            @Override
            public void destroy(Dependency dependency) {
                System.out.println("MyDependency1Provider > destroy");
            }
        }

    }


    public static class MyDependency2 implements SingletonDependency {

        public static class MyDependency2Provider implements DependencyProvider {
            @Override
            public Dependency create() {
                System.out.println("MyDependency2Provider > create");
                return new MyDependency2();
            }

            @Override
            public void initialize(Dependency dependency) {
                System.out.println("MyDependency2Provider > initialize");
            }

            @Override
            public void destroy(Dependency dependency) {
                System.out.println("MyDependency2Provider > destroy");
            }
        }

    }

}
