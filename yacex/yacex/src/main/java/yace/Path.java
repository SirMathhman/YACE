package yace;

import java.io.IOException;
import java.util.Optional;

/**
 * Abstraction for data sources that exist "permanently', i.e. files.
 */
public interface Path {
    /**
     * @return Whether this path exists within the file system.
     */
    boolean exists();

    /**
     * Creates this file on the file system.
     * If this path has already been created,
     * then {@link IOException} will be thrown.
     *
     * @throws IOException If the file already exists,
     *                     or any other internal error.
     */
    File createAsFile() throws IOException;

    /**
     * @return a file representing the existence of this path, or nothing if the file didn't exist.
     * @throws IOException when the file exists, but it not a file.
     */
    Optional<File> existingAsFile() throws IOException;
}
