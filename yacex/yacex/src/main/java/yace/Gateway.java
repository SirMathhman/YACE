package yace;

import java.nio.file.Path;
import java.util.Set;

/**
 * Represents an object which offers passage between code and an external I/O system.
 */
public interface Gateway {
    /**
     * Collects all the sources present.
     * @return The sources.
     */
    Set<Path> collectSources();
}
