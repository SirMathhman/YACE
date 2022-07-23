package yace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void requests_input() {
        var future = CompletableFuture.runAsync(() -> run(""));
        assertThrows(TimeoutException.class, () -> {
            try {
                future.get(10, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void exit(){
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> run("exit"));
    }

    private void run(String input) {
        try (var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())))) {
            while(true) {
                var line = reader.readLine();
                if(line != null && line.equals("exit")) return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
