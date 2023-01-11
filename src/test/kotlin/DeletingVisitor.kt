import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

object DeletingVisitor : SimpleFileVisitor<Path>() {
    override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        if (file != null) {
            Files.delete(file)
        }
        return FileVisitResult.CONTINUE
    }

    override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
        if (dir != null) {
            Files.delete(dir)
        }
        return FileVisitResult.CONTINUE
    }
}