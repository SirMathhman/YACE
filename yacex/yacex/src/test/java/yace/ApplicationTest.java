package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path currentWorkingDirectory;

    @Test
    void requests_input() {
        var future = CompletableFuture.runAsync(() -> new Application(currentWorkingDirectory, InputStream.nullInputStream()).run());
        assertThrows(TimeoutException.class, () -> {
            try {
                future.get(10, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void exit() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> runImpl());
    }

    @BeforeEach
    void setUp() throws IOException {
        currentWorkingDirectory = Files.createTempDirectory("yace-test");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(currentWorkingDirectory, new SimpleFileVisitor<>() {
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
    void deleteNotExists() {
        assertThrows(OperationException.class, () -> runImpl("delete(\"Test.java\")"));
    }

    @Test
    void deleteExists() throws IOException {
        var child = createTempFile("Test.java");
        runImpl("delete(\"Test.java\")");
        assertFalse(Files.exists(child));
    }

    private Path createTempFile(String other) throws IOException {
        var child = currentWorkingDirectory.resolve(other);
        Files.createFile(child);
        return child;
    }

    @Test
    void deleteAnother() throws IOException {
        var child = createTempFile("Test1.java");
        runImpl("delete(\"Test1.java\")");
        assertFalse(Files.exists(child));
    }

    private void runImpl(String... list) {
        new Application(currentWorkingDirectory, new ByteArrayInputStream((list.length == 0
                ? "exit"
                : String.join("\n", list) + "\nexit").getBytes())).run();
    }

}
