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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        assertImport(name, 1);
    }

    private void assertImport(String name, int countExclusive) throws IOException {
        var names = IntStream.range(0, countExclusive)
                .mapToObj(value -> "A" + value)
                .collect(Collectors.toSet());

        var input = names.stream()
                .map(value -> String.format("import %s;", value))
                .collect(Collectors.joining("\n"));

        var sourceName = name + ".java";
        Files.writeString(working.resolve(sourceName), input);
        run(Set.of(sourceName));

        var actual = Files.readString(working.resolve(name + ".mgs"));
        var joinedNames = names.stream().sorted()
                .collect(Collectors.joining(", ", "{ ", " }"));

        assertEquals("import " + joinedNames, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void import_siblings(int count) throws IOException {
        assertImport("Test", count + 1);
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
                var input = Files.readString(sourceFile);
                var lines = input.split(";");

                var imports = new ArrayList<String>();
                for (var line : lines) {
                    var stripped = line.strip();

                    var prefix = "import ";
                    if (stripped.startsWith(prefix)) {
                        var name = stripped.substring(prefix.length()).trim();
                        imports.add(name);
                    } else {
                        throw new RuntimeException("Invalid token: " + stripped);
                    }
                }

                Files.writeString(target, "import " + imports.stream()
                        .sorted()
                        .collect(Collectors.joining(", ", "{ ", " }")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
