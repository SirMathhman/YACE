package yace.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yace.CompileException;
import yace.MismatchException;
import yace.io.Path;
import yace.io.VirtualFileSystem;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {
    private Path source;
    private Path target;

    @BeforeEach
    void setUp() {
        var system = new VirtualFileSystem();
        source = system.resolve("Index.java");
        target = system.resolve("Index.mgs");
    }

    @Test
    void content() throws IOException {
        source.createAsFile().writeString("class Index {}");
        new Application(source, target).run();
        assertTrue(target.exists());
    }

    @Test
    void different_class_name() throws IOException {
        source.createAsFile().writeString("class Test {}");
        assertThrows(MismatchException.class, () -> new Application(source, target).run());
    }

    @Test
    void empty() throws IOException {
        source.createAsFile();
        assertThrows(CompileException.class, () -> new Application(source, target).run());
    }
}