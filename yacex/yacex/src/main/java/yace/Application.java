package yace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Application {
    private final InputStream stream;
    private final Path workingDirectory;

    public Application(InputStream stream, Path workingDirectory) {
        this.stream = stream;
        this.workingDirectory = workingDirectory;
    }

    void run() {
        try (var reader = new BufferedReader(new InputStreamReader(stream))) {
            while (true) {
                var line = reader.readLine();
                if (line == null) continue;
                if (new Executor(line, workingDirectory).execute()) return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
