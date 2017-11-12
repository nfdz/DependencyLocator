import io.github.nfdz.dependencylocator.DependencyLocator;
import persistence.PersistenceDependency;
import persistence.impl.PersistenceDependencyImpl.PersistenceDependencyImplProvider;
import printer.PrinterDependency;
import printer.impl.PrinterDependencyImpl.PrinterDependencyImplProvider;
import reader.ReaderDependency;
import reader.impl.ReaderDependencyImpl.ReaderDependencyImplProvider;

public class Main {

    public static void main(String []args) {
        setDependencies();
        int dataToProcess = 100;
        new MainDataProcessor().process(dataToProcess);
    }

    public static void setDependencies() {
        DependencyLocator.provide(PrinterDependency.class, new PrinterDependencyImplProvider());
        DependencyLocator.provide(ReaderDependency.class, new ReaderDependencyImplProvider());
        DependencyLocator.provide(PersistenceDependency.class, new PersistenceDependencyImplProvider());
    }

}