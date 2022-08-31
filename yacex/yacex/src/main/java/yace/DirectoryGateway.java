package yace;

import java.nio.file.Path;
import java.util.Set;

/**
 * Implements a Gateway under a given directory.
 * All files present in that directory will be returned as sources.
 */
public class DirectoryGateway implements Gateway {
    private final Path root;

    public DirectoryGateway(Path root) {
        this.root = root;
    }

    @Override
    public Set<Path> collectSources() {
        return null;
    }
}
