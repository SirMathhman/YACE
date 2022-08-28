package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ApplicationTest {
    private Optional<Path> working = Optional.empty();

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

    @Test
    void invalid() {
        Assertions.assertThrows(EmptyFileException.class, this::run);
    }

    @Test
    void valid() throws ApplicationException, IOException {
        Files.writeString(resolveSource(), "class Test {}");
        run();
    }

    private void run() throws ApplicationException {
        String input;
        try {
            input = Files.readString(resolveSource());
        } catch (IOException e) {
            throw new ApplicationException(e);
        }

        if(input.isEmpty()) {
            throw new EmptyFileException();
        }
    }

    private Path resolveSource() {
        return working.orElseThrow().resolve("Index.java");
    }
}
