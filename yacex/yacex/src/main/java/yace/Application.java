package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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

    static Optional<AnalysisResult> analyze(Path source) throws IOException {
        var input = Files.readString(source);
        if (input.length() == 0) {
            return Optional.of(new EmptySourceError(source));
        }
        var strippedInput = input.strip();
        if (strippedInput.startsWith("class")) {
            var bodyStart = strippedInput.indexOf('{');
            if(strippedInput.equals("class") || bodyStart == -1) {
                return Optional.of(new ClassStructureError(strippedInput));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.of(new EmptySourceError(source));
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

                var builder = new ClassBuilder();
                ClassBuilder withName;
                if(name.isEmpty()) {
                    withName = builder;
                } else {
                    withName = builder
                            .setKeywordSuffix(1)
                            .setName(name);
                }

                var withBody = attachBody(strippedInput, withName, bodyStart);
                output = withBody.build().render();
            } else {
                output = strippedInput;
            }

            Files.writeString(source, output);
        }
    }
}