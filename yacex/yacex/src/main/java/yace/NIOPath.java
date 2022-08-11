package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thin wrapper for {@link Path}.
 * Provides abstraction during testing.
 */
public class NIOPath implements yace.Path {
    private final Path value;

    /**
     * The provided raw value.
     * @param value The value.
     */
    public NIOPath(Path value) {
        this.value = value;
    }

    @Override
    public boolean exists() {
        return Files.exists(value);
    }

    @Override
    public void createAsFile() throws IOException {
        Files.createFile(value);
    }
}
