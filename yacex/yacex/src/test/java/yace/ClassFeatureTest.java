package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

public class ClassFeatureTest extends ApplicationFeatureTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_leading_whitespace(int spacing) {
        assertClassFormat(builder -> builder.setPrefix(spacing));
    }

    private void assertClassFormat(Function<ClassBuilder, ClassBuilder> unformatter) {
        var builder = new ClassBuilder();
        var classBuilder = unformatter.apply(builder);
        assertRenderableFormat(classBuilder.build(), new ClassNode());
    }

    private void assertRenderableFormat(ClassNode input, ClassNode output) {
        assertFormat(input.render(), output.render());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_format_trailing_whitespace(int spacing) {
        assertClassFormat(builder -> builder.setInfix(spacing));
    }

    @Test
    void class_name_analyze() {
        assertAnalyzeClass(new ClassNode());
    }

    private void assertAnalyzeClass(ClassNode input) {
        assertAnalyzeError(input.render(), path -> new ClassStructureError(new ClassNode().render()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_leading_whitespace(int spacing) {
        assertAnalyzeClass(builder -> builder.setPrefix(spacing));
    }

    private void assertAnalyzeClass(Function<ClassBuilder, ClassBuilder> function) {
        var builder = new ClassBuilder();
        var classBuilder = function.apply(builder);
        assertAnalyzeClass(classBuilder.build());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_name_analyze_trailing_whitespace(int spacing) {
        assertAnalyzeClass(builder -> builder.setInfix(spacing));
    }
}
