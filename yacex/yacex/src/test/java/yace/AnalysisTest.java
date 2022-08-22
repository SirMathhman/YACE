package yace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AnalysisTest {
    @Test
    void equals_valid() {
        var first = new Analysis("value");
        var second = new Analysis("value");
        assertEquals(first, second);
    }

    @Test
    void equals_invalid_message() {
        var first = new Analysis("first");
        var second = new Analysis("second");
        assertNotEquals(first, second);
    }
}