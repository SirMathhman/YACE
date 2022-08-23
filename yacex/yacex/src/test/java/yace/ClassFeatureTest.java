package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

public class ClassFeatureTest extends ApplicationFeatureTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_keyword_format_leading_whitespace(int spacing) {
        assertClassKeyword(builder -> builder.setPrefix(spacing));
    }

    private void assertClassKeyword(Function<ClassBuilder, ClassBuilder> unformatter) {
        assertClass(unformatter, new ClassNode());
    }

    private void assertClass(Function<ClassBuilder, ClassBuilder> unformatter, ClassNode output) {
        var builder = new ClassBuilder();
        var classBuilder = unformatter.apply(builder);
        assertRenderableFormat(classBuilder.build(), output);
    }

    private void assertRenderableFormat(ClassNode input, ClassNode output) {
        assertFormat(input.render(), output.render());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_keyword_format_trailing_whitespace(int spacing) {
        assertClassKeyword(builder -> builder.setInfix(spacing));
    }

    @Test
    void class_keyword_analyze() {
        assertAnalyzeClass(new ClassNode());
    }

    private void assertAnalyzeClass(ClassNode input) {
        assertAnalyzeError(input.render(), path -> new ClassStructureError(new ClassNode().render()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_keyword_analyze_leading_whitespace(int spacing) {
        assertAnalyzeClass(builder -> builder.setPrefix(spacing));
    }

    private void assertAnalyzeClass(Function<ClassBuilder, ClassBuilder> function) {
        var builder = new ClassBuilder();
        var classBuilder = function.apply(builder);
        assertAnalyzeClass(classBuilder.build());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_analyze_infix(int spacing) {
        assertAnalyzeClass(builder -> builder.setInfix(spacing));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void class_keyword_format_name(String name) {
        assertClass(builder -> builder.setName(name), new ClassBuilder()
                .setInfix(1)
                .setName(name)
                .build());
    }
}
