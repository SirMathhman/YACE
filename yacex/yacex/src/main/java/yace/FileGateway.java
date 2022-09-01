package yace;

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
public class FileGateway extends PathGateway {
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
    protected Path computeRoot() {
        return source.getParent();
    }

    @Override
    public Stream<Module> read() {
        return Files.exists(source) ?
                Stream.of(PathModule.createFromAbsolute(source.getParent(), source)) :
                Stream.empty();
    }
}
