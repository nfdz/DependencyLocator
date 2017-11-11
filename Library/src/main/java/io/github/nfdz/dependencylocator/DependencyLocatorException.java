package io.github.nfdz.dependencylocator;

/**
 * Custom exception for DependencyLocator. It extends RuntimeException to avoid the creation of try-catch block
 * because in normal cases it will never be thrown.
 */
public class DependencyLocatorException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public DependencyLocatorException(String msg) {
        super(msg);
    }

}
