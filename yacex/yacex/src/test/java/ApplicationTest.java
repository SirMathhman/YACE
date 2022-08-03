import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class ApplicationTest {

    private Path directory;

    @BeforeEach
    void setUp() throws IOException {
        directory = Files.createTempDirectory("working");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void nothing() {

    }

    @Test
    void missing() {
        assertMissing("Empty.java");
    }

    private void assertMissing(String... files) {
        Assertions.assertThrows(IOException.class, () -> {
            try {
                run(Set.of(files));
            } catch (RuntimeException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void missing_multiple() {
        assertMissing("First.java", "Second.java");
    }

    private void run(Set<String> files) {
        files.forEach(file -> {
            if (!Files.exists(directory.resolve(file))) {
                throw new RuntimeException(new IOException());
            }
        });
    }
}
