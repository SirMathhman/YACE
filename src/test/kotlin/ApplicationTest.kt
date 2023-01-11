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
        gateway = PathGateway(working)
    }

    @AfterEach
    fun tearDown() {
        Files.walkFileTree(working, DeletingVisitor)
    }

    private lateinit var gateway: PathGateway

    @Test
    fun generates_another_target() {
        val name = "Another"
        val otherSource = resolveSource(name)
        Files.createFile(otherSource)
        assertEquals(PathGateway(working).resolveTarget(name), run(otherSource))
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
        assertEquals(PathGateway(working).resolveTarget("Index"), run())
    }

    private fun run(): Path? {
        return run(resolveSource())
    }

    private fun run(source: Path): Path? {
        return Application(source, gateway).run()
    }


    private fun resolveSource(name: String = "Index"): Path = working.resolve("$name.kt")

    @Test
    fun generates_no_target() {
        run()
        assertFalse(doesTargetExist())
    }

    private fun doesTargetExist() = Files.exists(PathGateway(working).resolveTarget("Index"))
}