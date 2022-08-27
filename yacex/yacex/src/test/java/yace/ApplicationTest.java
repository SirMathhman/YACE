package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // format, analyze, refactor, compile
    @Test
    void package_format() throws IOException {
        assertFormat(0, 1, "test");
    }

    @Test
    void package_format_name() throws IOException {
        assertFormat(0, 1, "name");
    }

    private void assertFormat(int prefixLength, int infixLength, String name) throws IOException {
        var input = new PackageStatement(new Padding(prefixLength), new Padding(infixLength), name).render();
        var source = working.orElseThrow().resolve("Index.java");
        Files.writeString(source, input);

        var sourceInput = Files.readString(source);
        var sourceOutput = Arrays.stream(sourceInput.strip().split(" "))
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());
        Files.writeString(source, sourceOutput.get(0) + " " + sourceOutput.get(1));

        var output = Files.readString(source);
        Assertions.assertEquals(new PackageStatement(new Padding(0), new Padding(1), name).render(), output);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void package_format_prefix(int prefixLength) throws IOException {
        assertFormat(prefixLength, 1, "test");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void package_format_infix(int infixLength) throws IOException {
        assertFormat(0, infixLength, "test");
    }
}
