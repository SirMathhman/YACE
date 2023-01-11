import java.nio.file.Path

class PathGateway(private val root: Path) {
    fun resolveTarget(name: String): Path = root.resolve("$name.mgs")
}