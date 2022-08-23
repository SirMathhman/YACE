package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public Application() {
    }

    private static ClassBuilder attachBody(String strippedInput, ClassBuilder withName, int bodyStart) {
        ClassBuilder withBody;
        var bodyEnd = strippedInput.indexOf('}');
        if (bodyStart == -1 || bodyEnd == -1) {
            withBody = withName;
        } else {
            var body = strippedInput.substring(bodyStart, bodyEnd + 1).strip();
            withBody = withName
                    .setNameSuffix(1)
                    .setBody(body);
        }
        return withBody;
    }

    AnalysisResult analyze(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.isBlank()) {
            return new EmptySourceError(source);
        } else {
            return new ClassStructureError(input.strip());
        }
    }

    void format(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.length() != 0) {
            String output;
            var strippedInput = input.strip();
            if (strippedInput.startsWith("class")) {
                var bodyStart = strippedInput.indexOf('{');
                var nameEnd = bodyStart == -1 ? strippedInput.length() : bodyStart;
                var name = strippedInput.substring("class".length(), nameEnd).strip();

                var withName = new ClassBuilder()
                        .setKeywordSuffix(1)
                        .setName(name);
                var withBody = attachBody(strippedInput, withName, bodyStart);
                output = withBody.build().render();
            } else {
                output = strippedInput;
            }

            Files.writeString(source, output);
        }
    }
}