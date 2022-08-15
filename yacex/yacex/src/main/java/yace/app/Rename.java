package yace.app;

import yace.io.File;

import java.io.IOException;
import java.util.Optional;

public class Rename {
    private final boolean shouldPreview;
    private final String replacement;

    public Rename(boolean shouldPreview, String replacement) {
        this.shouldPreview = shouldPreview;
        this.replacement = replacement;
    }

    Optional<String> perform(File source, String input, String actualName) throws IOException {
        var output = input.replace(actualName, replacement);
        if (shouldPreview) {
            return Optional.of(output);
        } else {
            source.writeAsString(output);
            return Optional.empty();
        }
    }
}
