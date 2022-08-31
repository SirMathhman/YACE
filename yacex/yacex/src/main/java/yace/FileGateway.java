package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p>
 * Implements {@link Gateway}
 * using a single file location.
 * If the file location is empty,
 * then an empty stream is returned
 * when calling {@link #read()}.
 * Otherwise, the file is present.
 * </p>
 */
public class FileGateway implements Gateway {
    private final Path source;

    /**
     * Creates a new FileGateway using the single source location.
     *
     * @param source The location which may or may not exist.
     */
    public FileGateway(Path source) {
        this.source = source;
    }

    @Override
    public void write(Module module) throws IOException {
        Files.createFile(module.getPath());
    }

    @Override
    public Stream<Module> read() {
        return Files.exists(source) ?
                Stream.of(new Module(source)) :
                Stream.empty();
    }
}
