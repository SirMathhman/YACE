package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest extends FileTest {

    @Test
    void generates_no_target() {
        assertFalse(Files.exists(resolveTarget()));
    }

    @Test
    void generate_target() throws IOException {
        Files.createFile(resolveSource());
        var target = resolveTarget();
        new Application(new FileGateway(resolveSource())).run();
        assertTrue(Files.exists(target));
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
