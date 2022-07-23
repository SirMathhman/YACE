package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        var future = CompletableFuture.runAsync(() -> run(""));
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
        var child = currentWorkingDirectory.resolve("Test.java");
        Files.createFile(child);

        runImpl("delete(\"Test.java\")");
        assertFalse(Files.exists(child));
    }

    @Test
    void deleteAnother() throws IOException {
        var child = currentWorkingDirectory.resolve("Test1.java");
        Files.createFile(child);

        runImpl("delete(\"Test1.java\")");
        assertFalse(Files.exists(child));
    }

    private void runImpl(String... list) {
        run(list.length == 0
                ? "exit"
                : String.join("\n", list) + "\nexit", currentWorkingDirectory);
    }

    private void run(String input) {
        run(input, Paths.get("."));
    }

    private void run(String input, Path working) {
        try (var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())))) {
            while (true) {
                var line = reader.readLine();
                if (line == null) continue;
                if (line.equals("exit")) return;
                if (line.startsWith("delete(\"")) {
                    var slice = line.substring("delete(\"".length());
                    var separator = slice.indexOf('\"');
                    var name = slice.substring(0, separator);
                    var child = working.resolve(name);
                    try {
                        Files.delete(child);
                    } catch (IOException e) {
                        throw new OperationException(String.format("The child at '%s' did not exist.", child));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
