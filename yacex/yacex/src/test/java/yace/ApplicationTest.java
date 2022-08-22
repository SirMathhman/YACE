package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertFormat("");
    }

    private void format(Path source) throws IOException {
        var input = Files.readString(source);
        if(input.length() != 0) {
            Files.writeString(source, input.strip());
        }
    }

    private Path createSource() {
        return createSource("");
    }

    private Path createSource(String value) {
        try {
            var resolve = workingDirectory.orElseThrow().resolve("Index.java");
            Files.writeString(resolve, value);
            return resolve;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void empty_analyze() {
        var source = createSource();
        assertEquals(new EmptySourceError(source), analyze(source));
    }

    private static EmptySourceError analyze(Path source) {
        return new EmptySourceError(source);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_format(String value) throws IOException {
        assertFormat(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_analyze(String value) {
        var source = createSource(value);
        assertEquals(new EmptySourceError(source), analyze(source));
    }

    private void assertFormat(String value) throws IOException {
        var source = createSource(value);
        format(source);
        assertEquals("", Files.readString(source));
    }
}
