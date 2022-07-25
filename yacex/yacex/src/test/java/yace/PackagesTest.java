package yace;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackagesTest extends IntegrationTest {
    @Test
    void package_create_another() {
        createPackage(SecondaryName);
        assertTrue(Files.exists(WorkingDirectory.resolve(SecondaryName)));
    }

    @Test
    void package_create() {
        createPackage(PrimaryName);
        assertTrue(Files.exists(PrimaryDirectory));
    }

    @Test
    void package_create_parent() {
        createPackage(SecondaryName, PrimaryName);
        assertTrue(Files.exists(WorkingDirectory.resolve(SecondaryName).resolve(PrimaryName)));
    }

    @Test
    void package_create_another_parent() {
        createPackage(PrimaryName, SecondaryName);
        assertTrue(Files.exists(WorkingDirectory.resolve(PrimaryName).resolve(SecondaryName)));
    }

    @Test
    void package_create_directory() {
        createPackage(PrimaryName);
        assertTrue(Files.isDirectory(PrimaryDirectory));
    }
}
