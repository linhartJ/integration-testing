package itests

import itests.dao.TestCommitProvider
import itests.data.Commit
import itests.model.CrTool
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CrToolTest : IntegrationTest(TestApplicationConfiguration::class) {

    private val crTool = bean<CrTool>("crTool")
    private val commitDao = bean<TestCommitProvider>("testCommitProvider")

    @Test
    fun `resolveCommitStats returns empty statistics given no commits are available`() {
        // given
        commitDao.commits = emptyList()

        // when
        val result = runBlocking { crTool.resolveCommitStats() }

        // then
        assertEquals(0, result.commitsWithReview)
        assertEquals(0, result.commitsWithoutReview)
        assertEquals(0, result.commitsByAuthor.size)
        assertEquals(0, result.reviewsByAuthor.size)
    }

    @Test
    fun `resolveCommitStats correctly resolves commitsWithoutReview given all commits have not been reviewed`() {
        // given
        commitDao.commits = listOf(
                Commit("superman", "ISC-1 stuff CR: batman"),
                Commit("batman", "ISC-2 stuff CR: flash"),
                Commit("batman", "ISC-2 stuff CR: batman"),
                Commit("flash", "ISC-3 stuff"),
                Commit("superman", "ISC-1 stuff CR: wonderwoman"),
                Commit("wonderwoman", "ISC-4 stuff CR: flash")
        )

        // when
        val result = runBlocking { crTool.resolveCommitStats() }

        // then
        assertEquals(1, result.commitsWithoutReview)
    }

    @Test
    fun `resolveCommitsStats correctly resolves commitsWithReview given all comits have not been reviewed`() {
        // given
        commitDao.commits = listOf(
                Commit("superman", "ISC-1 stuff CR: batman"),
                Commit("batman", "ISC-2 stuff CR: flash"),
                Commit("batman", "ISC-2 stuff CR: batman"),
                Commit("flash", "ISC-3 stuff"),
                Commit("superman", "ISC-1 stuff CR: wonderwoman"),
                Commit("wonderwoman", "ISC-4 stuff CR: flash")
        )

        // when
        val result = runBlocking { crTool.resolveCommitStats() }

        // then
        assertEquals(5, result.commitsWithReview)
    }

    @Test
    fun `resolveCommitsStats resolves commitsByAuthor correctly`() {
        // given
        commitDao.commits = listOf(
                Commit("superman", "ISC-1 stuff CR: batman"),
                Commit("batman", "ISC-2 stuff CR: flash"),
                Commit("batman", "ISC-2 stuff CR: batman"),
                Commit("flash", "ISC-3 stuff"),
                Commit("superman", "ISC-1 stuff CR: wonderwoman"),
                Commit("wonderwoman", "ISC-4 stuff CR: flash")
        )

        // when
        val result = runBlocking { crTool.resolveCommitStats().commitsByAuthor }

        // then
        assertEquals(4, result.size)
        assertEquals(2, result["superman"])
        assertEquals(2, result["batman"])
        assertEquals(1, result["flash"])
        assertEquals(1, result["wonderwoman"])
    }

    @Test
    fun `resolveCommitsStats resolves reviewsByAuthor correctly`() {
        // given
        commitDao.commits = listOf(
                Commit("superman", "ISC-1 stuff CR: batman"),
                Commit("batman", "ISC-2 stuff CR: flash"),
                Commit("batman", "ISC-2 stuff CR: batman"),
                Commit("flash", "ISC-3 stuff"),
                Commit("superman", "ISC-1 stuff CR: wonderwoman"),
                Commit("wonderwoman", "ISC-4 stuff CR: flash")
        )

        // when
        val result = runBlocking { crTool.resolveCommitStats().reviewsByAuthor }

        // then
        assertEquals(3, result.size)
        assertEquals(2, result["batman"])
        assertEquals(2, result["flash"])
        assertEquals(1, result["wonderwoman"])
    }

}