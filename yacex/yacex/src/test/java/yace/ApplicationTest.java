package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ApplicationTest {

    private Path working;

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new SimpleFileVisitor<>() {
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

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void package_create(String name) throws IOException {
        createPackage(name);
        Assertions.assertTrue(Files.exists(working.resolve(name)));
    }

    private void createPackage(String input) throws IOException {
        run("createSource(\"" + input + "\")");
    }

    private void run(String temp) throws IOException {
        var prefix = "createSource(\"";
        if (temp.startsWith(prefix)) {
            var start = prefix.length();
            var end = temp.indexOf('\"', prefix.length());
            var name = temp.substring(start, end);
            Files.createDirectories(working.resolve(name));
        } else {
            throw new IllegalArgumentException("Invalid input: " + temp);
        }
    }

    @Test
    void unknown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> run(""));
    }
}
