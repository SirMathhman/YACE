package yace;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void blocks() {
        var future = CompletableFuture.runAsync(this::run);
        assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
    }

    @Test
    void file_create() {
        runWithString("create(\"Test\")");
    }

    @Test
    void exit() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> runWithString());
    }

    private void runWithString(String... args) {
        var input = args.length == 0 ? "exit" : String.join("\n", args) + "\nexit";
        run(new ByteArrayInputStream(input.getBytes()));
    }

    private void run() {
        run(InputStream.nullInputStream());
    }

    private void run(InputStream stream) {
        try (var reader = new BufferedReader(new InputStreamReader(stream))) {
            while (true) {
                var line = reader.readLine();
                if (line == null) continue;
                if (line.equals("exit")) return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
