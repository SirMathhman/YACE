package yace.module;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CollectionModuleTest {

    @Test
    void streamPackage() {
        var expected = List.of("first", "second", "third");
        var actual = new CollectionModule("Index", expected.toArray(String[]::new))
                .streamPackage()
                .collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }

    @Test
    void resolveSibling() {
        var expected = new CollectionModule("Next", "package");
        var actual = new CollectionModule("Previous", "package")
                .resolveSibling("Next");
        assertEquals(expected, actual);
    }

    @Test
    void computeName() {
        var expected = "Next";
        var actual = new CollectionModule(expected).computeName();
        assertEquals(expected, actual);
    }
}