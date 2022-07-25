package yace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class IntegrationTest {
    protected static final String PrimaryName = "Test";
    protected static final String SecondaryName = "AnotherTest";
    protected static Path WorkingDirectory;
    protected Path PrimaryPath;
    protected Path SecondaryPath;
    protected Path PrimaryDirectory;

    protected static void runWithString(String... args) {
        var input = args.length == 0 ? "exit" : String.join("\n", args) + "\nexit";
        new Application(new ByteArrayInputStream(input.getBytes()), WorkingDirectory).run();
    }

    protected static void invoke(String caller, String... args) {
        runWithString(caller + "(\"" + String.join(",", args) + "\")");
    }

    protected static void createPackage(String... args) {
        IntegrationTest.invoke(Executor.CreatePackage, args);
    }

    @BeforeEach
    void setUp() throws IOException {
        WorkingDirectory = Files.createTempDirectory("test");
        PrimaryPath = WorkingDirectory.resolve(PrimaryName + "." + Executor.PlatformExtension);

        SecondaryPath = WorkingDirectory.resolve(SecondaryName + "." + Executor.PlatformExtension);
        PrimaryDirectory = WorkingDirectory.resolve(PrimaryName);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walkFileTree(WorkingDirectory, new SimpleFileVisitor<>() {
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
}
