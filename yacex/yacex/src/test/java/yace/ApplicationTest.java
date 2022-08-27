package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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

    // format, analyze, refactor, compile
    @Test
    void package_format() throws IOException {
        assertFormat(0);
    }

    private void assertFormat(int prefixCount) throws IOException {
        var value = "package test;";
        var input = " ".repeat(prefixCount) + value;
        var source = working.orElseThrow().resolve("Index.java");
        Files.writeString(source, input);

        var sourceInput = Files.readString(source);
        var sourceOutput = sourceInput.strip();
        Files.writeString(source, sourceOutput);

        var output = Files.readString(source);
        Assertions.assertEquals(value, output);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void package_format_prefix(int prefixLength) throws IOException {
        assertFormat(prefixLength);
    }

    private static class DeletingVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
