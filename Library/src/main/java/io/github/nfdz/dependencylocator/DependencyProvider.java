package io.github.nfdz.dependencylocator;

public interface DependencyProvider {

    Dependency create();
    void initialize(Dependency dependency);
    void destroy(Dependency dependency);

}
