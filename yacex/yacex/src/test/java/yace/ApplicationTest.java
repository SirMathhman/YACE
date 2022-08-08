package yace;

import org.junit.jupiter.api.AfterEach;
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
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void test() {
        assertFalse(Files.exists(working.resolve("First.mgs")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void source_does_not_exist(String name) {
        assertThrows(SourceDoesNotExistException.class, () -> {
            run(Collections.singleton(name + ".java"));
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void sources_do_not_exist(int extent) {
        var sources = IntStream.range(0, extent)
                .mapToObj(String::valueOf)
                .map(value -> "Test" + value + ".java")
                .collect(Collectors.toSet());
        assertThrows(SourceDoesNotExistException.class, () -> run(sources));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void target_exists(String name) throws IOException {
        Files.createFile(working.resolve(name + ".java"));
    }

    private void run(Set<String> sources) throws SourceDoesNotExistException {
        for (String source : sources) {
            throw new SourceDoesNotExistException(working.resolve(source));
        }
    }
}
