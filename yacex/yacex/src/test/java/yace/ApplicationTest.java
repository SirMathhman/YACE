package yace;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest extends IntegrationTest {

    @Test
    void blocks() {
        var future = CompletableFuture.runAsync(ApplicationTest::run);
        assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
    }

    @Test
    void exit() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> runWithString());
    }

    private static void run() {
        new Application(InputStream.nullInputStream(), WorkingDirectory).run();
    }
}
