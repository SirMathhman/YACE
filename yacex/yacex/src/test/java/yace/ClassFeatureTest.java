package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassFeatureTest extends ApplicationFeatureTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_prefix(int spacing) {
        assertClassKeyword(builder -> builder.setPrefix(spacing));
    }

    private void assertClassKeyword(Function<ClassBuilder, ClassBuilder> unformatter) {
        assertClass(unformatter, new ClassNode());
    }

    private void assertClass(Function<ClassBuilder, ClassBuilder> unformatter, ClassNode output) {
        var builder = new ClassBuilder();
        var classBuilder = unformatter.apply(builder);
        assertFormat(classBuilder.build().render(), output.render());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_keyword_format_trailing_whitespace(int spacing) {
        assertClassKeyword(builder -> builder.setKeywordSuffix(spacing));
    }

    @Test
    void class_keyword_analyze() {
        assertAnalyzeClassError(new ClassNode());
    }

    private void assertAnalyzeClassError(ClassNode input) {
        assertAnalyzeError(input.render(), path -> new ClassStructureError(new ClassNode().render()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_keyword_analyze_leading_whitespace(int spacing) {
        assertAnalyzeClassError(builder -> builder.setPrefix(spacing));
    }

    private void assertAnalyzeClassError(Function<ClassBuilder, ClassBuilder> function) {
        var builder = new ClassBuilder();
        var classBuilder = function.apply(builder);
        assertAnalyzeClassError(classBuilder.build());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_analyze_infix(int spacing) {
        assertAnalyzeClassError(builder -> builder.setKeywordSuffix(spacing));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void class_name_format(String name) {
        assertClassFormat(name, 0, "", 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_suffix_format(int spacing) {
        assertClassFormat("Test", spacing, "", 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{temp}"})
    void class_body_format(String body) {
        assertClassFormat("Test", 0, body, 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void class_body_suffix(int spacing) {
        assertClassFormat("Test", 0, "{}", spacing);
    }

    @Test
    void class_body_analyze() throws IOException {
        var source = createSource("class Test {}");
        var analyze = Application.analyze(source);
        assertTrue(analyze.isEmpty());
    }

    private void assertClassFormat(String name, int spacing, String body, int bodySuffix) {
        var expectedWithName = new ClassBuilder()
                .setKeywordSuffix(1)
                .setName(name);

        ClassBuilder expectedWithBody;
        if (body.isEmpty()) {
            expectedWithBody = expectedWithName;
        } else {
            expectedWithBody = expectedWithName
                    .setNameSuffix(1)
                    .setBody(body);
        }

        assertClass(builder -> builder.setName(name)
                .setNameSuffix(spacing)
                .setBody(body)
                .setBodySuffix(bodySuffix), expectedWithBody
                .build());
    }
}
