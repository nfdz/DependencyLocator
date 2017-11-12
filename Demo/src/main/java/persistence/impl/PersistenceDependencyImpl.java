package persistence.impl;

import io.github.nfdz.dependencylocator.Dependency;
import io.github.nfdz.dependencylocator.DependencyProvider;
import persistence.PersistenceDependency;

import java.util.Random;

public class PersistenceDependencyImpl implements PersistenceDependency {

    public static class PersistenceDependencyImplProvider implements DependencyProvider {

        @Override
        public Dependency create() {
            return new PersistenceDependencyImpl();
        }

        @Override
        public void destroy(Dependency dependency) {
            // nothing to do
        }
    }

    @Override
    public int getLastCounter() {
        return new Random().nextInt(100);
    }

}
