package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}