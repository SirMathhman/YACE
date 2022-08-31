package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private Optional<Path> workingDirectory = Optional.empty();

    @BeforeEach
    void setUp() throws IOException {
        workingDirectory = Optional.of(Files.createTempDirectory("working"));
    }

    @AfterEach
    void tearDown() {
        workingDirectory.ifPresent(value -> {
            try {
                Files.walkFileTree(value, new DeletingVisitor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void generates_no_target() {
        assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generate_target() throws IOException {
        Files.createFile(resolveSource());
        var target = resolveTarget();
        new Application(resolveSource()).run();
        assertTrue(Files.exists(target));
    }

    private Path resolveSource() {
        return resolveFromWorking("Index.java");
    }

    private Path resolveFromWorking(String other) {
        return workingDirectory.orElseThrow().resolve(other);
    }

    private Path resolveTarget() {
        return resolveFromWorking("Index.mgs");
    }
}
