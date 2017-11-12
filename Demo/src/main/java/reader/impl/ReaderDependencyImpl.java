package reader.impl;

import data.DummyData;
import io.github.nfdz.dependencylocator.Dependency;
import io.github.nfdz.dependencylocator.DependencyLocator;
import io.github.nfdz.dependencylocator.DependencyProvider;
import persistence.PersistenceDependency;
import reader.ReaderDependency;

public class ReaderDependencyImpl implements ReaderDependency {

    public static class ReaderDependencyImplProvider implements DependencyProvider {

        @Override
        public Dependency create() {
            return new ReaderDependencyImpl();
        }

        @Override
        public void destroy(Dependency dependency) {
            // nothing to do
        }
    }

    private int counter;

    private ReaderDependencyImpl() {
        PersistenceDependency persistence = (PersistenceDependency)DependencyLocator.locate(PersistenceDependency.class);
        counter = persistence.getLastCounter();
    }


    @Override
    public DummyData read() {
        return new DummyData(Integer.toString(++counter));
    }

}
