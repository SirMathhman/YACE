import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class ApplicationTest {
    private lateinit var path: Path

    @BeforeEach
    fun setUp() {
        path = Files.createTempDirectory("working")
    }

    @AfterEach
    fun tearDown() {
        Files.walkFileTree(path, DeletingVisitor)
    }

    @Test
    fun test() {
        assertFalse(Files.exists(path))
    }
}