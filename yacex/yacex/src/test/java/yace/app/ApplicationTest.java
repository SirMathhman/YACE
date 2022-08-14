package yace.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yace.CompileException;
import yace.MismatchException;
import yace.io.Path;
import yace.io.VirtualFileSystem;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        writeSource("class Index {}");
        runEmpty();
        assertTrue(target.exists());
    }

    @Test
    void different_class_name() throws IOException {
        writeSource("class Test {}");
        assertThrows(MismatchException.class, this::runEmpty);
    }

    private void runEmpty() {
        new Application(source, target).run(Optional.empty());
    }

    @Test
    void task_no_target() throws IOException {
        rename();
        assertFalse(target.exists());
    }

    @Test
    void rename_class() throws IOException {
        rename();
        assertEquals("class Bar {}", source.existingAsFile().orElseThrow()
                .readAsString());
    }

    private void rename() throws IOException {
        writeSource("class Foo {}");
        new Application(source, target).run(Optional.of("Bar"));
    }

    private void writeSource(String content) throws IOException {
        source.createAsFile().writeAsString(content);
    }

    @Test
    void empty() throws IOException {
        source.createAsFile();
        assertThrows(CompileException.class, this::runEmpty);
    }
}