package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        var source = createSource();
        assertEquals("", Files.readString(source));
    }

    private Path createSource() {
        try {
            var resolve = workingDirectory.orElseThrow().resolve("Index.java");
            Files.createFile(resolve);
            return resolve;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void empty_analyze() {
        var source = createSource();
        assertEquals(new EmptySourceError(source), new EmptySourceError(source));
    }
}
