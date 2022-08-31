package yace;

import java.nio.file.Path;

/**
 * Represents a location which has code.
 */
public class Module {
    private final Path path;

    /**
     * The path to the file.
     * @param path The path.
     */
    public Module(Path path) {
        this.path = path;
    }

    /**
     * Resolves a sibling module.
     * A sibling module has the exact same parent package as this module,
     * just with a different name.
     * @param name The name of the sibling.
     * @return The sibling.
     */
    Module resolveSibling(String name) {
        return new Module(getPath().resolveSibling(name));
    }

    /**
     * Computes the name of this module.
     * The name is not guaranteed to be unique.
     * The name is the last part of file name.
     * E.g. "java\lang\Index.java" => "Index".
     * @return The name.
     */
    String computeName() {
        var fileName = getPath().getFileName().toString();
        var extensionSeparator = fileName.indexOf('.');
        return fileName.substring(0, extensionSeparator);
    }

    public Path getPath() {
        return path;
    }
}
