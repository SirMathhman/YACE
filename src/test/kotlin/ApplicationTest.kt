import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class ApplicationTest {
    private lateinit var working: Path

    @BeforeEach
    fun setUp() {
        working = Files.createTempDirectory("working")
    }

    @AfterEach
    fun tearDown() {
        Files.walkFileTree(working, DeletingVisitor)
    }

    @Test
    fun generates_target() {
        Files.createFile(resolveSource())
        run()
        assertTrue(doesTargetExist())
    }

    @Test
    fun generates_correct_target() {
        Files.createFile(resolveSource())
        assertEquals(resolveMagmaFile("Index"), run())
    }

    private fun run(): Path? {
        val source = resolveSource()
        if (Files.exists(source)) {
            val nameCount = source.nameCount
            val lastIndex = nameCount - 1
            val lastValueWithSeparator = source.getName(lastIndex).toString()
            val separator = lastValueWithSeparator.indexOf('.')
            val lastValue = lastValueWithSeparator.substring(0, separator)
            val target = resolveMagmaFile(lastValue)
            Files.createFile(target)
            return target
        }
        return null
    }

    private fun resolveSource(): Path = working.resolve("Index.kt")

    @Test
    fun generates_no_target() {
        run()
        assertFalse(doesTargetExist())
    }

    private fun doesTargetExist() = Files.exists(resolveMagmaFile("Index"))

    private fun resolveMagmaFile(name: String): Path = working.resolve("$name.mgs")
}