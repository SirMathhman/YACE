package yace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    public Application() {
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
            if(strippedInput.startsWith("class")) {
                var name = strippedInput.substring("class".length());
                var strippedName = name.strip();
                output = new ClassBuilder()
                        .setInfix(1)
                        .setName(strippedName)
                        .build()
                        .render();
            } else {
                output = strippedInput;
            }

            Files.writeString(source, output);
        }
    }
}