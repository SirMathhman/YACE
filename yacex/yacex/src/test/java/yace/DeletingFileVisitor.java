package yace;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * An implementation of {@link java.nio.file.FileVisitor}
 * which, in conjunction with {@link Files#walkFileTree(Path, FileVisitor)},
 * deletes the passed directory in addition to the files within it.
 */
class DeletingFileVisitor extends SimpleFileVisitor<Path> {
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
}
