package yace;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Simple implementation of {@link SimpleFileVisitor} with type {@link Path}
 * that deletes all the files within a directory.
 * Typically used with {@link Files#walkFileTree(Path, FileVisitor)}.
 * Such usage would result in the aforementioned behavior as well as the directory itself being deleted.
 */
class DeletingVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }
}
