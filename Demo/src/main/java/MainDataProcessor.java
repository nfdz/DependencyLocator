import data.DummyData;
import io.github.nfdz.dependencylocator.DependencyLocator;
import printer.PrinterDependency;
import reader.ReaderDependency;

public class MainDataProcessor {

    private ReaderDependency reader;
    private PrinterDependency printer;

    public MainDataProcessor() {
        reader = (ReaderDependency)DependencyLocator.locate(ReaderDependency.class);
        printer = (PrinterDependency)DependencyLocator.locate(PrinterDependency.class);
    }

    public void process(int dataToProcess) {
        for (int i = 0; i < dataToProcess; i++) {
            DummyData data = reader.read();
            printer.print(data);
        }
    }

}
