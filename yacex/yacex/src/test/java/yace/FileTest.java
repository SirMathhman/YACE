package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileTest {
    protected Optional<Path> working = Optional.empty();

    @BeforeEach
    void setUp() throws IOException {
        working = Optional.of(Files.createTempDirectory("working"));
    }

    @AfterEach
    void tearDown() {
        working.ifPresent(value -> {
            try {
                Files.walkFileTree(value, new DeletingVisitor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
