package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ClassFeatureTest extends ApplicationFeatureTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_leading_whitespace(int spacing) {
        assertFormat(createSpacing(spacing) + " class", "class");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_trailing_whitespace(int spacing) {
        assertFormat(" class" + createSpacing(spacing), "class");
    }

    private static String createSpacing(int spacing) {
        return " ".repeat(spacing);
    }

    @Test
    void class_name_analyze(){
        assertAnalyzeError("class", path -> new ClassStructureError("class"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_leading_whitespace(int spacing) {
        assertAnalyzeError(createSpacing(spacing) + "class", path -> new ClassStructureError("class"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_trailing_whitespace(int spacing) {
        assertAnalyzeError("class" + createSpacing(spacing), path -> new ClassStructureError("class"));
    }
}
