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
    void empty() {
        assertThrows(EmptySourceException.class, this::runImpl);
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
        return runImpl(String.format("class %s {}", CLASS_NAME));
    }

    private void runImpl() throws IOException {
        runImpl("");
    }

    private Set<Path> runImpl(String input) throws IOException {
        var source = resolveSource();
        Files.writeString(source, input);
        return Application.fromSingleGateway(new FileGateway(source)).run();
    }

    @Test
    void generate_target() throws IOException {
        runWithEmptyClass();
        assertTrue(Files.exists(resolveTarget()));
    }

    private Path resolveSource() {
        return resolveFromWorking(CLASS_NAME + ".java");
    }

    private Path resolveFromWorking(String other) {
        return working.orElseThrow().resolve(other);
    }

    private Path resolveTarget() {
        return resolveFromWorking(CLASS_NAME + ".mgs");
    }
}
