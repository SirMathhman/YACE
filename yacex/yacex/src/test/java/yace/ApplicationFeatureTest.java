package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ApplicationFeatureTest {
    protected final Application application = new Application();
    protected Optional<Path> workingDirectory = Optional.empty();

    @BeforeEach
    void setUp() throws IOException {
        workingDirectory = Optional.of(Files.createTempDirectory("working"));
    }

    @AfterEach
    void tearDown() {
        workingDirectory.ifPresent(path -> {
            try {
                Files.walkFileTree(path, new DeletingVisitor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected Path createSource(String value) {
        try {
            var resolve = workingDirectory.orElseThrow().resolve("Index.java");
            Files.writeString(resolve, value);
            return resolve;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void assertFormatsToEmpty(String value) {
        assertFormat(value, "");
    }

    void assertFormat(String input, String output) {
        try {
            var source = createSource(input);
            application.format(source);
            assertEquals(output, Files.readString(source));
        } catch (IOException e) {
            fail(e);
        }
    }

    protected void assertAnalyzeError(String value, Function<Path, ?> toError) {
        try {
            var source = createSource(value);
            var apply = toError.apply(source);
            assertEquals(apply, new Application().analyze(source));
        } catch (IOException e) {
            fail(e);
        }
    }
}
