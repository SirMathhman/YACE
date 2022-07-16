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

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    @Test
    void test() {
        var future = CompletableFuture.runAsync(() -> {
            var scanner = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
            while (true) {
                try {
                    scanner.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        assertThrows(TimeoutException.class, () -> {
            try {
                future.get(1, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }
}
