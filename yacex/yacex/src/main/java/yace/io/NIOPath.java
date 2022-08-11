package yace.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Thin wrapper for {@link Path}.
 * Provides abstraction during testing.
 */
public class NIOPath implements yace.io.Path {
    private final Path value;

    /**
     * The provided raw value.
     *
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
    public File createAsFile() throws IOException {
        Files.createFile(value);
        return null;
    }

    @Override
    public Optional<File> existingAsFile() {
        return Files.exists(value) ? Optional.of(new NIOFile()) : Optional.empty();
    }

    @Override
    public String toString() {
        return value.toAbsolutePath().toString();
    }

    private class NIOFile implements File {
        @Override
        public void writeString(String content) throws IOException {
            Files.writeString(value, content);
        }

        @Override
        public String readAsString() throws IOException {
            return Files.readString(value);
        }

        @Override
        public String getName() {
            return value.getFileName().toString();
        }
    }
}
