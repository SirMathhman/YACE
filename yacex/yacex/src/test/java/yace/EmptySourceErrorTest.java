package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptySourceErrorTest {
    @Test
    void apply() throws IOException {
        var path = Paths.get(".", "Index.java");
        Files.createFile(path);
        new EmptySourceError(path).apply();

        assertEquals("class Index {\n}", Files.readString(path));
        Files.deleteIfExists(path);
    }
}