package yace;

import java.io.IOException;

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
    void createAsFile() throws IOException;
}
