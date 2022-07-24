package yace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ApplicationTest {
    @Test
    void test() {
        var future = CompletableFuture.runAsync(() -> {
            try (var reader = new BufferedReader(new InputStreamReader(InputStream.nullInputStream()))) {
                while (true) {
                    reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Assertions.assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
    }
}
