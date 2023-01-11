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
    fun generates_correct_target(){
        Files.createFile(resolveSource())
        assertEquals(resolveTarget(), run())
    }

    private fun run(): Path? {
        if (Files.exists(resolveSource())) {
            val target = resolveTarget()
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

    private fun doesTargetExist() = Files.exists(resolveTarget())

    private fun resolveTarget(): Path = working.resolve("Index.mgs")
}