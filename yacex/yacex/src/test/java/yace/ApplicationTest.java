package yace;

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
import java.util.Optional;

public class ApplicationTest {
    private Optional<Path> workingDirectory = Optional.empty();

    @BeforeEach
    void setUp() throws IOException {
        workingDirectory = Optional.of(Files.createTempDirectory("working"));
    }

    @AfterEach
    void tearDown() {
        workingDirectory.ifPresent(path -> {
            try {
                Files.walkFileTree(path, new DeletingVisitor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // format, analyze, refactor, compile
    @Test
    void empty_format() throws IOException {
        var source = workingDirectory.orElseThrow();
        Files.createFile(source.resolve("Index.java"));
        Assertions.assertEquals("", Files.readString(source));
    }

    private static class DeletingVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.deleteIfExists(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.deleteIfExists(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
