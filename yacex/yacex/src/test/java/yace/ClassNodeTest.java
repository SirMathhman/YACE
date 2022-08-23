package yace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassNodeTest {
    @Test
    void renderWithName() {
        var expected = "classTest";
        var actual = new ClassBuilder()
                .setName("Test")
                .build()
                .render();
        assertEquals(expected, actual);
    }
}