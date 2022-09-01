package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest extends FileTest {

    @Test
    void generates_no_target() {
        assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generate_proper_target() throws IOException {
        var source = resolveSource();
        Files.createFile(source);

        var expected = Collections.singleton(resolveTarget());
        var actual = new Application(new FileGateway(source)).run();
        assertEquals(1, actual.size());
        assertEquals(first(expected), first(actual));
    }

    private static Path first(Set<Path> actual) {
        return actual.stream().findFirst().orElseThrow();
    }

    @Test
    void generate_target() throws IOException {
        var source = resolveSource();
        Files.createFile(source);
        new Application(new FileGateway(source)).run();
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
