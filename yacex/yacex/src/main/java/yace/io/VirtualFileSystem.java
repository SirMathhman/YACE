package yace.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a file system implemented within memory.
 */
public class VirtualFileSystem implements FileSystem {
    private final Map<String, File> identifiers;

    /**
     * Creates a file system.
     */
    public VirtualFileSystem() {
        identifiers = new HashMap<>();
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
        private final Map<String, File> identifiers;

        private VirtualPath(Map<String, File> identifiers, String identifier) {
            this.identifiers = identifiers;
            this.identifier = identifier;
        }

        @Override
        public boolean exists() {
            return identifiers.containsKey(identifier);
        }

        @Override
        public File createAsFile() throws IOException {
            if (exists()) {
                throw new IOException("File already exists.");
            }

            return createFileImpl();
        }

        private VirtualFile createFileImpl() {
            var file = new VirtualFile(identifier);
            identifiers.put(identifier, file);
            return file;
        }

        @Override
        public Optional<File> existingAsFile() {
            return exists() ?
                    Optional.ofNullable(identifiers.get(identifier)) :
                    Optional.of(createFileImpl());
        }

        private static class VirtualFile implements File {
            private final String identifier;
            private String content = "";

            public VirtualFile(String identifier) {
                this.identifier = identifier;
            }

            @Override
            public void writeString(String content) {
                this.content = content;
            }

            @Override
            public String readAsString() {
                return content;
            }

            @Override
            public String getName() {
                return identifier;
            }
        }
    }
}
