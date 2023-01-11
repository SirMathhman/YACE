import java.nio.file.Files
import java.nio.file.Path

class Application(private val source: Path, private val gateway: PathGateway) {
    fun run(): Path? {
        if (Files.exists(source)) {
            val nameCount = source.nameCount
            val lastIndex = nameCount - 1
            val lastValueWithSeparator = source.getName(lastIndex).toString()
            val separator = lastValueWithSeparator.indexOf('.')
            val lastValue = lastValueWithSeparator.substring(0, separator)
            val target = gateway.resolveTarget(lastValue)
            Files.createFile(target)
            return target
        }
        return null
    }
}