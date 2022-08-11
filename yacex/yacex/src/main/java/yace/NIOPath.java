package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thin wrapper for {@link Path}.
 * Provides abstraction during testing.
 */
public class NIOPath {
    private final Path value;

    /**
     * The provided raw value.
     * @param value The value.
     */
    public NIOPath(Path value) {
        this.value = value;
    }

    /**
     *
     * @return Whether this path exists within the file system.
     */
    boolean isExists() {
        return Files.exists(value);
    }

    /**
     * Creates this file on the file system.
     * @throws IOException If the file could not be created.
     */
    void createAsFile() throws IOException {
        Files.createFile(value);
    }
}
