package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

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
        run();
        Assertions.assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generates_target() throws IOException {
        Files.createFile(resolveSource());
        run();
        Assertions.assertTrue(Files.exists(resolveTarget()));
    }

    private void run() throws IOException {
        if (Files.exists(resolveSource())) {
            Files.createFile(resolveTarget());
        }
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
