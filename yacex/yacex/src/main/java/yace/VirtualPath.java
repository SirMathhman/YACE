package yace;

import java.io.IOException;

/**
 * Implements Path in-memory.
 * Useful for unit tests.
 */
public class VirtualPath implements Path {
    private boolean exists = false;

    @Override
    public boolean exists() {
        return exists;
    }

    @Override
    public void createAsFile() throws IOException {
        if (exists) {
            throw new IOException("File already exists.");
        }
        exists = true;
    }
}
