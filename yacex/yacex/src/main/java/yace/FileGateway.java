package yace;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Implements {@link Gateway}
 * using a single file location.
 * If the file location is empty,
 * then an empty stream is returned
 * when calling {@link #collectSources()}.
 * Otherwise, the file is present.
 * </p>
 */
public class FileGateway implements Gateway {
    private final Path source;

    /**
     * Creates a new FileGateway using the single source location.
     * @param source The location which may or may not exist.
     */
    public FileGateway(Path source) {
        this.source = source;
    }

    @Override
    public Set<Path> collectSources() {
        var sources = new HashSet<Path>();
        if (Files.exists(source)) {
            sources.add(source);
        }
        return sources;
    }
}
