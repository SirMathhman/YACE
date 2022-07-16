package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    private Path temp;

    @BeforeEach
    void setUp() throws IOException {
        temp = Files.createTempFile("Test", ".java");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(temp);
    }

    @Test
    void invalid() {
        assertThrows(RuntimeException.class, () -> run(Set.of(temp)));
    }

    private void run(Set<Path> sources) throws IOException {
        for (Path path : sources) {
            var input = Files.readString(path);
            if(input.isBlank()) {
                var format = "Content of file at location '%s' cannot be blank.";
                var message = String.format(format, path.toAbsolutePath());
                throw new RuntimeException(message);
            }
        }
    }

    @Test
    void does_nothing() {
        assertDoesNotThrow(() -> run(Collections.emptySet()));
    }
}
