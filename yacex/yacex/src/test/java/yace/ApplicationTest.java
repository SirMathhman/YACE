package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ApplicationTest {
    private Path working;

    private static void format(Path source) throws IOException {
        var input = Files.readString(source);
        var output = input.isBlank() ? "" : renderFormattedClass();
        Files.writeString(source, output);
    }

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new DeletingVisitor());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void format_blank(int spacing) {
        assertFormat(" ".repeat(spacing), "");
    }

    private void assertFormat(String input, String output) {
        try {
            var source = working.resolve("Index.java");
            Files.writeString(source, input);
            format(source);
            assertEquals(output, Files.readString(source));
        } catch (IOException e) {
            fail(e);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void format_whitespace_before_class_keyword(int spacing) {
        assertFormat(renderClass(" ".repeat(spacing), ""), renderFormattedClass());
    }

    private static String renderFormattedClass() {
        return renderClass("", "\n");
    }

    private static String renderClass(String beforeClassWhitespace, String beforeBrace) {
        return beforeClassWhitespace + "class Test {" + beforeBrace + "}";
    }

    private static class DeletingVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
