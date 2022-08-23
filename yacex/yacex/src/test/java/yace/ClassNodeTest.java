package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassNodeTest {
    private static void assertRender(String output, Function<ClassBuilder, ClassBuilder> initializer) {
        var builder = new ClassBuilder().setName("Test");
        var actual = initializer.apply(builder)
                .build()
                .render();
        assertEquals(output, actual);
    }

    @Test
    void renderWithName() {
        assertRender("classTest", builder -> builder);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void renderWithNameSuffix(int spacing) {
        assertRender("classTest" + new Spacing(spacing).render(), builder -> builder.setNameSuffix(spacing));
    }

    @Test
    void renderWithBody(){
        assertRender("classTest{}", builder -> builder.setBody("{}"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void renderWithBodySuffix(int spacing) {
        assertRender("classTest{}" + new Spacing(spacing).render(), builder -> builder
                .setBody("{}")
                .setBodySuffix(spacing));
    }
}