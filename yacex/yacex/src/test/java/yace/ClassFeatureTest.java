package yace;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ClassFeatureTest extends ApplicationFeatureTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void format_class(int spacing) {
        assertFormat(" ".repeat(spacing) + " class", "class");
    }
}
