package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VirtualPathTest {

    @Test
    void exists() {
        assertFalse(new VirtualPath().exists());
    }

    @Test
    void createAsFile() throws IOException {
        var path = new VirtualPath();
        path.createAsFile();
        assertTrue(path.exists());
    }

    @Test
    void createAsFileAlreadyExists() {
        assertThrows(IOException.class, () -> {
            var path = new VirtualPath();
            path.createAsFile();
            path.createAsFile();
        });
    }
}