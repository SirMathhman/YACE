package yace;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryGatewayTest extends FileTest {
    @Test
    void collectSources() throws IOException {
        var first = createSource("First");
        var second = createSource("Second");
        var expected = Set.of(first, second);
        var actual = new DirectoryGateway(working.orElseThrow())
                .streamSources1()
                .collect(Collectors.toSet());

        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    private Path createSource(String name) throws IOException {
        var path = working.orElseThrow().resolve(String.format("%s.java", name));
        Files.createFile(path);
        return path;
    }
}