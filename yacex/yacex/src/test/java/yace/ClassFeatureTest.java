package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ClassFeatureTest extends ApplicationFeatureTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_leading_whitespace(int spacing) {
        assertFormat(new ClassRenderer(spacing, 0).render(), new ClassRenderer(0, 0).render());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_trailing_whitespace(int spacing) {
        assertFormat(new ClassRenderer(0, spacing).render(), new ClassRenderer(0, 0).render());
    }

    @Test
    void class_name_analyze() {
        assertAnalyzeError(new ClassRenderer(0, 0).render(), path -> new ClassStructureError(new ClassRenderer(0, 0).render()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_leading_whitespace(int spacing) {
        assertAnalyzeError(new ClassRenderer(spacing, 0).render(), path -> new ClassStructureError(new ClassRenderer(0, 0).render()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_trailing_whitespace(int spacing) {
        assertAnalyzeError(new ClassRenderer(0, spacing).render(), path -> new ClassStructureError(new ClassRenderer(0, 0).render()));
    }
}
