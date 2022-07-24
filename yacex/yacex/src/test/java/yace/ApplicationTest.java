package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static Path TempDirectory;

    private static final String PrimaryName = "Test";

    private static final String SecondaryName = "AnotherTest";

    private final String PackageCreate = invokeCreate("Packages");

    private final String SourcesCreate = invokeCreate("Sources");
    private Path PrimaryPath;
    private Path SecondaryPath;
    private final String PlatformExtension = "java";
    private Path PrimaryDirectory;

    private String invokeCreate(String name) {
        return name + ".create";
    }

    @BeforeEach
    void setUp() throws IOException {
        TempDirectory = Files.createTempDirectory("test");
        PrimaryPath = TempDirectory.resolve(PrimaryName + "." + PlatformExtension);

        SecondaryPath = TempDirectory.resolve(SecondaryName + "." + PlatformExtension);
        PrimaryDirectory = TempDirectory.resolve(PrimaryName);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(TempDirectory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void blocks() {
        var future = CompletableFuture.runAsync(this::run);
        assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
    }

    private void createSource(String name) {
        create(SourcesCreate, name);
    }

    private void create(String caller, String name) {
        runWithString(caller + "(\"" + name + "\")");
    }

    @Test
    void package_create() {
        createPackage(PrimaryName);
        assertTrue(Files.exists(PrimaryDirectory));
    }

    @Test
    void package_create_directory() {
        createPackage(PrimaryName);
        assertTrue(Files.isDirectory(PrimaryDirectory));
    }

    @Test
    void file_create() {
        createSource(PrimaryName);
        assertTrue(Files.exists(PrimaryPath));
    }

    @Test
    void package_create_another() {
        createPackage(SecondaryName);
        assertTrue(Files.exists(TempDirectory.resolve(SecondaryName)));
    }

    private void createPackage(String name) {
        create(PackageCreate, name);
    }


    @Test
    void file_create_another() {
        createSource(SecondaryName);
        assertTrue(Files.exists(SecondaryPath));
    }

    @Test
    void file_create_content() throws IOException {
        createSource(PrimaryName);
        assertEquals(renderClass(PrimaryName), Files.readString(PrimaryPath));
    }

    private String renderClass(String name) {
        return "class " + name + " {\n}";
    }

    @Test
    void file_create_content_another() throws IOException {
        createSource(SecondaryName);
        assertEquals(renderClass(SecondaryName), Files.readString(SecondaryPath));
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
                if (execute(line)) return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean execute(String line) throws IOException {
        if (line.equals("exit")) return true;
        else if (line.contains("(") && line.endsWith(")")) {
            var argStart = line.indexOf("(");
            var caller = line.substring(0, argStart);
            var argEnd = line.length() - 1;

            var argString = line.substring(argStart + 1, argEnd).strip();
            var name = argString.substring(1, argString.length() - 1);

            if (caller.equals(SourcesCreate)) {
                Files.writeString(TempDirectory.resolve(name + "." + PlatformExtension), renderClass(name));
                return false;
            } else if (caller.equals(PackageCreate)) {
                Files.createDirectory(TempDirectory.resolve(name));
                return false;
            } else {
                throw new IllegalArgumentException("Invalid caller: " + caller);
            }
        } else {
            throw new IllegalArgumentException("Invalid line: " + line);
        }
    }
}
