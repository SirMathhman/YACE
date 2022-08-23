package yace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmptyFeatureTest extends ApplicationFeatureTest {

    // format, analyze, refactor, compile
    @Test
    void empty_format() {
        assertFormatsToEmpty("");
    }

    @Test
    void empty_analyze() {
        assertAnalyzeError("", EmptySourceError::new);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_format(String value) {
        assertFormatsToEmpty(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace_analyze(String value) {
        assertAnalyzeError(value, EmptySourceError::new);
    }
}
