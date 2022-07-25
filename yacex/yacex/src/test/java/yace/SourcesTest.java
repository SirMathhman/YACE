package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SourcesTest extends IntegrationTest {
    private static void createSource(String name) {
        create(Executor.CreateSource, name);
    }

    @Test
    void file_create() {
        createSource(PrimaryName);
        assertTrue(Files.exists(PrimaryPath));
    }

    @Test
    void file_create_another() {
        createSource(SecondaryName);
        assertTrue(Files.exists(SecondaryPath));
    }

    @Test
    void file_create_content() throws IOException {
        createSource(PrimaryName);
        assertEquals(new ClassRenderer(PrimaryName).renderClass(), Files.readString(PrimaryPath));
    }

    @Test
    void file_create_content_another() throws IOException {
        createSource(SecondaryName);
        assertEquals(new ClassRenderer(SecondaryName).renderClass(), Files.readString(SecondaryPath));
    }
}
