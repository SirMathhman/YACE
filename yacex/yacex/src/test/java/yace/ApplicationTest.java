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
        var actual = runImpl();

        var expected = Collections.singleton(resolveTarget());
        assertEquals(1, actual.size());
        assertEquals(first(expected), first(actual));
    }

    private Set<Path> runImpl() throws IOException {
        var source = resolveSource();
        Files.createFile(source);
        return Application.fromSingleGateway(new FileGateway(source)).run();
    }

    @Test
    void generate_target() throws IOException {
        var source = resolveSource();
        Files.createFile(source);
        Application.fromSingleGateway(new FileGateway(source)).run();
        assertTrue(Files.exists(resolveTarget()));
    }

    private Path resolveSource() {
        return resolveFromWorking("Index.java");
    }

    private Path resolveFromWorking(String other) {
        return working.orElseThrow().resolve(other);
    }

    private Path resolveTarget() {
        return resolveFromWorking("Index.mgs");
    }
}
