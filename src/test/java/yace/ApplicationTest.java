package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Path> working = Optional.empty();

    @BeforeEach
    void setUp() throws IOException {
        working = Optional.of(Files.createTempDirectory("working"));
    }

    @AfterEach
    void tearDown() {
        working.ifPresent(value -> {
            try {
                Files.walkFileTree(value, new DeletingVisitor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void does_not_generate_target() throws IOException {
        new Application(resolveSource(), resolveTarget()).run();
        assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generates_target() throws IOException {
        createAndRun("");
        assertTrue(Files.exists(resolveTarget()));
    }

    @Test
    void generates_proper_target() throws IOException {
        var value = createAndRun("");
        assertEquals(resolveTarget(), value);
    }

    private Path createAndRun(String input) throws IOException {
        Files.writeString(resolveSource(), input);
        return new Application(resolveSource(), resolveTarget()).run().orElseThrow();
    }

    @Test
    void empty() throws IOException {
        createAndRun("");
        assertTargetIsEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void whitespace(int length) throws IOException {
        createAndRun(" ".repeat(length));
        assertTargetIsEmpty();
    }

    private void assertTargetIsEmpty() throws IOException {
        assertTrue(Files.readString(resolveTarget()).isEmpty());
    }

    private Path resolveSource() {
        return resolveFromWorking("Index.java");
    }

    private Path resolveFromWorking(String other) {
        return working.orElseThrow().resolve(other);
    }

    private Path resolveTarget() {
        return resolveFromWorking("Index.mgs");
    }

    private static class DeletingVisitor extends SimpleFileVisitor<Path> {
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
    }
}
