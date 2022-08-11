package yace;

import java.io.IOException;

/**
 * Abstraction for data sources that exist "permanently', i.e. files.
 */
public interface Path {
    /**
     * @return Whether this path exists within the file system.
     */
    boolean isExists();

    /**
     * Creates this file on the file system.
     *
     * @throws IOException If the file could not be created.
     */
    void createAsFile() throws IOException;
}
