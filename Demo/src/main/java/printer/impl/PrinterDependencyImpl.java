package printer.impl;

import data.DummyData;
import io.github.nfdz.dependencylocator.Dependency;
import io.github.nfdz.dependencylocator.DependencyProvider;
import printer.PrinterDependency;

public class PrinterDependencyImpl implements PrinterDependency {

    public static class PrinterDependencyImplProvider implements DependencyProvider {

        @Override
        public Dependency create() {
            return new PrinterDependencyImpl();
        }

        @Override
        public void destroy(Dependency dependency) {
            // nothing to do
        }
    }

    @Override
    public void print(DummyData data) {
        System.out.println("Data: " + data.tag);
    }

}
