package reader;

import data.DummyData;
import io.github.nfdz.dependencylocator.Dependency;

public interface ReaderDependency extends Dependency {

    DummyData read();

}
