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
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void nothing() throws IOException {
        run(Collections.emptySet());
        try (var stream = Files.list(working)) {
            assertTrue(stream.collect(Collectors.toSet()).isEmpty());
        }
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

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void import_simple(String name) throws IOException {
        var expected = "import org.junit.jupiter.api.AfterEach";
        var sourceName = name + ".java";
        Files.writeString(working.resolve(sourceName), expected);
        run(Set.of(sourceName));

        var actual = Files.readString(working.resolve(name + ".mgs"));
        assertEquals(expected, actual);
    }

    private void run(Set<String> files) {
        files.forEach(source -> {
            var sourceFile = working.resolve(source);
            if (!Files.exists(sourceFile)) {
                throw new RuntimeException(new IOException());
            }
            var separator = source.indexOf('.');
            try {
                var nameWithoutExtension = source.substring(0, separator);
                var targetName = nameWithoutExtension + ".mgs";
                var target = working.resolve(targetName);
                var output = Files.readString(sourceFile);
                Files.writeString(target, output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
