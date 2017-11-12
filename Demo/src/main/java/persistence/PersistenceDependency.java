package persistence;

import io.github.nfdz.dependencylocator.Dependency;

public interface PersistenceDependency extends Dependency {

    int getLastCounter();

}
