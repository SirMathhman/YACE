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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    private Path working;
    private Path target;
    private Path source;

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
        source = working.resolve("Target.java");
        target = working.resolve("Target.mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new SimpleFileVisitor<>() {
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
        });
    }

    @Test
    void target_not_generated() throws IOException {
        run();
        assertFalse(doesTargetExist());
    }

    private boolean doesTargetExist() {
        return Files.exists(target);
    }

    @Test
    void target_generated() throws IOException {
        Files.writeString(source, "class Test {}");
        run();
        assertTrue(doesTargetExist());
    }

    private void run() throws IOException {
        if (Files.exists(source)) {
            Files.createFile(target);
        }
    }
}
