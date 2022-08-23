package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ClassFeatureTest extends ApplicationFeatureTest {
    private static String renderClass(int prefix, int suffix) {
        return createSpacing(prefix) + " class" + createSpacing(suffix);
    }

    private static String createSpacing(int spacing) {
        return " ".repeat(spacing);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_leading_whitespace(int spacing) {
        assertFormat(renderClass(spacing, 0), "class");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_trailing_whitespace(int spacing) {
        assertFormat(renderClass(0, spacing), renderClass(0, 0));
    }

    @Test
    void class_name_analyze() {
        assertAnalyzeError(renderClass(0, 0), path -> new ClassStructureError(renderClass(0, 0)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_leading_whitespace(int spacing) {
        assertAnalyzeError(renderClass(spacing, 0), path -> new ClassStructureError(renderClass(0, 0)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_trailing_whitespace(int spacing) {
        assertAnalyzeError(renderClass(0, spacing), path -> new ClassStructureError(renderClass(0, 0)));
    }
}
