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
        for (var file : sources) {
            var fullName = file.getFileName().toString();
            var separator = fullName.indexOf('.');
            var asAbsolute = file.toAbsolutePath();

            if(separator == -1) {
                var format = "File '%s' does not have an extension and therefore is not a Java source file.";
                var message = String.format(format, asAbsolute);
                throw new IllegalArgumentException(message);
            }

            var name1 = fullName.substring(0, separator);
            var extension = fullName.substring(separator + 1);
            if(extension.equals("java")) {
                var format = "File '%s' does not have an extension of '.java' and therefore is not a Java source file.";
                var message = String.format(format, asAbsolute);
                throw new IllegalArgumentException(message);
            }

            var input = Files.readString(file);
            if(input.isBlank()) {
                var format = "Error: Content of file at location '%s' cannot be blank.\n" +
                             "  Fix: Add class template:\n" +
                             "\n" +
                             "class " + name1 + " {\n" +
                             "}";
                var message = String.format(format, asAbsolute);
                throw new RuntimeException(message);
            }
        }
    }

    @Test
    void does_nothing() {
        assertDoesNotThrow(() -> run(Collections.emptySet()));
    }
}
