package yace.module;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PathModuleTest {
    @Test
    void streamPackage() {
        var parent = Paths.get("parent");
        var child = parent.resolve("first")
                .resolve("second")
                .resolve("third");

        var expected = List.of("first", "second");
        var actual = new PathModule(parent, child).streamPackage()
                .collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }
}