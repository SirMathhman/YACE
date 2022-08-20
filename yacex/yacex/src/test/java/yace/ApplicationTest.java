package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private Path working;

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new DeletingVisitor());
    }

    @Test
    void format_nothing() throws IOException {
        var source = working.resolve("Index.java");
        Files.createFile(source);
        assertEquals("", Files.readString(source));
    }

    private static class DeletingVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
