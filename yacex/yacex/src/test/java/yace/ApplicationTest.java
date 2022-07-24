package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final Path TestFile = Paths.get(".", "Test.java");
    public static final Path TestAnotherFile = Paths.get(".", "Test1.java");
    private final String PrimaryTestName = "Test";
    private final String SecondaryTestName = "Test1";
    private final String CreateSource = "Sources.create";

    @Test
    void blocks() {
        var future = CompletableFuture.runAsync(this::run);
        assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
    }

    @Test
    void file_create() {
        create(PrimaryTestName);
        assertTrue(Files.exists(TestFile));
    }

    private void create(String name) {
        runWithString(CreateSource + "(\"" + name + "\")");
    }

    @Test
    void file_create_another() {
        create(SecondaryTestName);
        assertTrue(Files.exists(TestAnotherFile));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TestFile);
        Files.deleteIfExists(TestAnotherFile);
    }

    @Test
    void file_create_content() throws IOException {
        create(PrimaryTestName);
        assertEquals(renderClass(PrimaryTestName), Files.readString(TestFile));
    }

    private String renderClass(String name) {
        return "class " + name + " {\n}";
    }

    @Test
    void file_create_content_another() throws IOException {
        create(SecondaryTestName);
        assertEquals(renderClass(SecondaryTestName), Files.readString(TestAnotherFile));
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
                if (line.startsWith(CreateSource + "(\"")) {
                    var slice = line.substring((CreateSource + "(\"").length());
                    var nameEnd = slice.indexOf('\"');
                    var name = slice.substring(0, nameEnd);
                    Files.writeString(Paths.get(".", name + ".java"), renderClass(name));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
