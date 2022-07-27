package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private final String className = "Target";
    private Path Target;

    @BeforeEach
    void setUp() throws IOException {
        Target = Files.createTempFile(className, ".java");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
    }

    @Test
    void test() throws IOException {
        var expected = renderClass();
        Files.writeString(Target, expected);
        assertEquals(expected, Files.readString(Target));
    }

    private String renderClass() {
        return "class " + className + " {\n}";
    }
}
