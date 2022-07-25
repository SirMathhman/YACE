package yace;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackagesTest extends IntegrationTest {
    @Test
    void package_create_another() {
        createPackage(SecondaryName);
        assertTrue(Files.exists(TempDirectory.resolve(SecondaryName)));
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
}
