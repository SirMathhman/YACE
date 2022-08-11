package yace.io;

/**
 * Represents a collection of files,
 * which may or may not be provided by the operating system.
 */
public interface FileSystem {
    /**
     * Resolves a file in this file system using the given name.
     * @param name The name of the file.
     * @return The path of the file.
     */
    Path resolve(String name);
}
