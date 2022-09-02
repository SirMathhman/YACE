package yace;

import org.junit.jupiter.api.Test;
import yace.gateway.FileGateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest extends FileTest {
    public static final String CLASS_NAME = "Index";

    private static Path first(Set<Path> actual) {
        return actual.stream().findFirst().orElseThrow();
    }

    @Test
    void emptyJava() {
        assertThrows(EmptySourceException.class, () -> runImpl("", "java", true));
    }

    @Test
    void blankJava() {
        assertThrows(EmptySourceException.class, () -> runImpl(" ", "java", true));
    }

    @Test
    void emptyMagma() throws IOException {
        assertCompile("");
    }

    @Test
    void blankMagma() throws IOException {
        assertCompile(" ");
    }

    private void assertCompile(String input) throws IOException {
        assertEquals("class Index {}", Files.readString(runImpl(input, "mgs", false)
                .stream()
                .findFirst()
                .orElseThrow()));
    }

    @Test
    void generates_no_target() {
        assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generate_proper_target() throws IOException {
        var actual = runWithEmptyClass();
        var expected = Collections.singleton(resolveTarget());
        assertEquals(1, actual.size());
        assertEquals(first(expected), first(actual));
    }

    private Set<Path> runWithEmptyClass() throws IOException {
        return runImpl(String.format("class %s {}", CLASS_NAME), "java", true);
    }

    private Set<Path> runImpl(String input, String extension, boolean isJava) throws IOException {
        var source = resolveSource(extension);
        Files.writeString(source, input);
        return Application.fromSingleGateway(new FileGateway(source), isJava).run();
    }

    @Test
    void generate_target() throws IOException {
        runWithEmptyClass();
        assertTrue(Files.exists(resolveTarget()));
    }

    private Path resolveSource(String extension) {
        return resolveFromWorking(CLASS_NAME + "." + extension);
    }

    private Path resolveFromWorking(String other) {
        return working.orElseThrow().resolve(other);
    }

    private Path resolveTarget() {
        return resolveFromWorking(CLASS_NAME + ".mgs");
    }
}
