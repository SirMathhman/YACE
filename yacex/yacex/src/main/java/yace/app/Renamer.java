package yace.app;

import java.io.IOException;
import java.util.Optional;

public class Renamer {
    public final String summary = "Rename Class";
    private final boolean shouldPreview;
    private final String replacement;

    public Renamer(boolean shouldPreview, String replacement) {
        this.shouldPreview = shouldPreview;
        this.replacement = replacement;
    }

    Optional<String> perform(Input input1) throws IOException {
        var output = input1.input.replace(input1.actualName, replacement);
        if (shouldPreview) {
            return Optional.of(output);
        } else {
            input1.source.writeAsString(output);
            return Optional.empty();
        }
    }

    public String createDetails() {
        return "To " + replacement;
    }
}
