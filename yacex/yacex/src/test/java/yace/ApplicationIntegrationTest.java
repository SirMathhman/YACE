package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationIntegrationTest {

    private Path working;
    private Path target;
    private Path source;

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
        source = working.resolve("Target.java");
        target = working.resolve("Target.mgs");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new DeletingFileVisitor());
    }

    @Test
    void target_not_generated() {
        new Application(new NIOPath(source), new NIOPath(target)).run();
        assertFalse(doesTargetExist());
    }

    private boolean doesTargetExist() {
        return Files.exists(target);
    }

    @Test
    void target_generated() throws IOException {
        Files.writeString(source, "class Target {}");
        new Application(new NIOPath(source), new NIOPath(target)).run();
        assertTrue(doesTargetExist());
    }
}
