package yace;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a file system implemented within memory.
 */
public class VirtualFileSystem implements FileSystem {
    private final Set<String> identifiers;

    /**
     * Creates a file system.
     */
    public VirtualFileSystem() {
        identifiers = new HashSet<>();
    }

    @Override
    public Path resolve(String name) {
        return new VirtualPath(identifiers, name);
    }

    /**
     * Implements Path in-memory.
     * Useful for unit tests.
     */
    public static class VirtualPath implements Path {
        private final String identifier;
        private final Set<String> identifiers;

        private VirtualPath(Set<String> identifiers, String identifier) {
            this.identifiers = identifiers;
            this.identifier = identifier;
        }

        @Override
        public boolean exists() {
            return identifiers.contains(identifier);
        }

        @Override
        public void createAsFile() throws IOException {
            if (exists()) {
                throw new IOException("File already exists.");
            }

            identifiers.add(identifier);
        }
    }
}
