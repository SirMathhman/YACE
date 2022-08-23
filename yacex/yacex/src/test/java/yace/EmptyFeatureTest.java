package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyFeatureTest extends ApplicationFeatureTest {

    // format, analyze, refactor, compile
    @Test
    void empty_format() {
        assertFormatsToEmpty("");
    }

    private Path createSource() {
        return createSource("");
    }

    @Test
    void empty_analyze() {
        var source = createSource();
        assertEquals(new EmptySourceError(source), new Application().analyze(source));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_format(String value) {
        assertFormatsToEmpty(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_analyze(String value) {
        var source = createSource(value);
        assertEquals(new EmptySourceError(source), new Application().analyze(source));
    }

}
