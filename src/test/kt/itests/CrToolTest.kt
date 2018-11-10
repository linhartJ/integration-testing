package itests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class CrToolTest {

    val crTool = AnnotationConfigApplicationContext(IntegrationTestingConfiguration::class.java)
            .getBean("crTool") as CrTool

    @Test
    fun `resolveCommitStats works`() {
        val result = crTool.resolveCommitStats()
        assertTrue(result.commitsWithoutReview > 0)
    }

}