package yace;

import java.util.stream.Stream;

/**
 * Represents a location containing code.
 */
public interface Module {
    /**
     * Streams the package components for this module.
     * The package is defined by the location of this module resolved against the working directory.
     *
     * @return The package.
     */
    Stream<String> streamPackage();

    /**
     * Resolves a sibling module.
     * A sibling module has the exact same parent package as this module,
     * just with a different name.
     *
     * @param name The name of the sibling.
     * @return The sibling.
     */
    Module resolveSibling(String name);

    /**
     * Computes the name of this module.
     * The name is not guaranteed to be unique.
     * The name is the last part of file name.
     * E.g. "java\lang\Index.java" => "Index".
     *
     * @return The name.
     */
    String computeName();
}
