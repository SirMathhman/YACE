package yace;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ApplicationTest {
    @Test
    void generate_target_not() {
        assertFalse(Files.exists(Paths.get(".", "Index.java")));
    }


}
