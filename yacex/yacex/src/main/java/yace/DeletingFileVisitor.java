package yace;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * An implementation of {@link java.nio.file.FileVisitor}
 * which, in conjunction with {@link Files#walkFileTree(java.nio.file.Path, FileVisitor)},
 * deletes the passed directory in addition to the files within it.
 */
class DeletingFileVisitor extends SimpleFileVisitor<java.nio.file.Path> {
    @Override
    public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException exc) throws IOException {
        Files.deleteIfExists(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs) throws IOException {
        Files.deleteIfExists(file);
        return FileVisitResult.CONTINUE;
    }
}
