package yace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private final Path working;
    private final InputStream input;

    public Application(Path working, InputStream input) {
        this.working = working;
        this.input = input;
    }

    void run() {
        try (var reader = new BufferedReader(new InputStreamReader(input))) {
            while (true) {
                var line = reader.readLine();
                if (line == null) continue;
                if (line.equals("exit")) return;
                if (line.startsWith("delete(\"")) {
                    var slice = line.substring("delete(\"".length());
                    var separator = slice.indexOf('\"');
                    var name = slice.substring(0, separator);
                    var child = working.resolve(name);
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
