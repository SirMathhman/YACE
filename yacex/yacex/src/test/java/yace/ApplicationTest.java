package yace;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    @Test
    void exits() {
        var future = runAsync("exit");
        assertDoesNotThrow(() -> future.get());
    }

    private CompletableFuture<Void> runAsync(String input) {
        return CompletableFuture.runAsync(() -> {
            var scanner = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
            while (true) {
                try {
                    var line = scanner.readLine();
                    if(line == null) continue;
                    if (line.equals("exit")) {
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Test
    void awaits_input() {
        var future = runAsync("");

        assertThrows(TimeoutException.class, () -> {
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
