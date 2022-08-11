package yace;

/**
 * Implements Path in-memory.
 * Useful for unit tests.
 */
public class VirtualPath implements Path {
    private  boolean exists = false;

    @Override
    public boolean exists() {
        return exists;
    }

    @Override
    public void createAsFile() {
        exists = true;
    }
}
