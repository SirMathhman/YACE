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

public class ApplicationTest {

    private Path working;

    @BeforeEach
    void setUp() throws IOException {
        working = Files.createTempDirectory("working");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(working, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void package_create(String name) throws IOException {
        run(new CreatePackage(name).render());
        assertPackageCreated(name);
    }

    private void assertPackageCreated(String name) {
        Assertions.assertTrue(Files.exists(working.resolve(name)));
    }

    @Test
    void package_create_pad_name_left() throws IOException {
        var value = new CreatePackage("test");
        value.leftNamePad = 1;
        run(value.render());
        assertPackageCreated("test");
    }

    @Test
    void package_create_pad_name_right() throws IOException {
        var value = new CreatePackage("test");
        value.rightNamePad = 1;
        run(value.render());
        assertPackageCreated("test");
    }

    @Test
    void package_create_without_opening_parentheses() {
        var value = new CreatePackage("test");
        value.toggleOpenParentheses();
        assertSyntaxFail(value);
    }

    private void assertSyntaxFail(CreatePackage value) {
        Assertions.assertThrows(SyntaxException.class, () -> run(value.render()));
    }

    @Test
    void package_create_without_ending_parentheses() {
        var value = new CreatePackage("test");
        value.toggleClosingParentheses();
        assertSyntaxFail(value);
    }

    private void run(String input) throws IOException {
        var prefix = "createSource";
        if (input.contains(prefix)) {
            var prefixIndex = input.indexOf(prefix);
            var value = input.substring(prefixIndex + prefix.length()).strip();
            if(value.indexOf('(') == -1) throw new SyntaxException("Cannot find opening parentheses", input);
            if(value.indexOf(')') == -1) throw new SyntaxException("Cannot find closing parentheses", input);

            var start = input.indexOf('\"');
            var end = input.indexOf('\"', start + 1);
            var name = input.substring(start + 1, end);
            Files.createDirectories(working.resolve(name));
        } else {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    @Test
    void unknown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> run(""));
    }
}
