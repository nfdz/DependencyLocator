package printer;

import data.DummyData;
import io.github.nfdz.dependencylocator.Dependency;

public interface PrinterDependency extends Dependency {

    void print(DummyData data);

}
