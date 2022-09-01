package yace.gateway;

import org.junit.jupiter.api.Test;
import yace.FileTest;
import yace.module.CollectionModule;
import yace.module.Module;
import yace.module.PathModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryGatewayTest extends FileTest {
    @Test
    void read() throws IOException {
        var first = createSource("First");
        var second = createSource("Second");
        var expected = Set.of(createModuleImpl(first), createModuleImpl(second));
        var actual = createGateway().read()
                .collect(Collectors.toSet());

        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    private Module createModuleImpl(Path first) {
        return PathModule.createFromAbsolute(working.orElseThrow(), first);
    }

    private PathGateway createGateway() {
        return new DirectoryGateway(working.orElseThrow());
    }

    private Path createSource(String name) throws IOException {
        var path = working.orElseThrow().resolve(String.format("%s.java", name));
        Files.createFile(path);
        return path;
    }

    @Test
    void write() throws IOException {
        var expected = working.orElseThrow().resolve("directory").resolve("File.mgs");
        var actual = createGateway().write(new CollectionModule("File", "directory"), "mgs");
        assertEquals(expected, actual);
        assertTrue(Files.exists(actual));
    }
}