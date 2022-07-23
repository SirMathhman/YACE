package yace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {
    private final Path currentWorkingDirectory;
    private final InputStream input;

    public Application(Path currentWorkingDirectory, InputStream input) {
        this.currentWorkingDirectory = currentWorkingDirectory;
        this.input = input;
    }

    void run() {
        try (var reader = new BufferedReader(new InputStreamReader(input))) {
            while (true) {
                var line = reader.readLine();
                if (line == null) continue;
                if (line.equals("exit")) return;
                if(line.startsWith("create(\"")) {
                    var slice = line.substring("create(\"".length());
                    var nameEnd = slice.indexOf('\"');
                    var name = slice.substring(0, nameEnd);
                    var separator = name.indexOf('.');
                    var nameWithoutExtension = name.substring(0, separator);
                    var output = "class " + nameWithoutExtension + " {\n}";

                    Files.writeString(currentWorkingDirectory.resolve(name), output);
                }
                if (line.startsWith("delete(\"")) {
                    var slice = line.substring("delete(\"".length());
                    var separator = slice.indexOf('\"');
                    var name = slice.substring(0, separator);
                    var child = currentWorkingDirectory.resolve(name);
                    try {
                        Files.delete(child);
                    } catch (IOException e) {
                        throw new OperationException(String.format("The child at '%s' did not exist.", child));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
