package itests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ApplicationConfiguration::class])
class CrToolTest {

    @Autowired
    private lateinit var crTool: CrTool

    @Test
    fun `resolveCommitStats works`() {
        val result = crTool.resolveCommitStats()
        assertTrue(result.commitsWithoutReview > 0)
    }
}
