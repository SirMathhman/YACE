package yace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VirtualPathTest {
    private VirtualFileSystem system;

    @BeforeEach
    void setUp() {
        system = new VirtualFileSystem();
    }

    @Test
    void exists() {
        assertFalse(system.resolve("test").exists());
    }

    @Test
    void createAsFile() throws IOException {
        var path = system.resolve("test");
        path.createAsFile();
        assertTrue(path.exists());
    }

    @Test
    void createAsFileAlreadyExists() {
        assertThrows(IOException.class, () -> {
            var path = system.resolve("test");
            path.createAsFile();
            path.createAsFile();
        });
    }

    @Test
    void createSameFileAlreadyExists() {
        assertThrows(IOException.class, () -> {
            var system = this.system;
            system.resolve("test").createAsFile();
            system.resolve("test").createAsFile();
        });
    }
}