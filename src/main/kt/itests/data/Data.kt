package itests.data

data class Commit(val author: String, val commitMessage: String): Groupable {
    override val groupId get() = author
}

data class ReviewedCommit(val commitAuthor: String, val reviewAuthor: String): Groupable {
    override val groupId get() = reviewAuthor
}

data class CommitStatistics(
        val commitsWithoutReview: Int,
        val commitsWithReview: Int,
        val commitsByAuthor: Map<String, Int>,
        val reviewsByAuthor: Map<String, Int>
)

interface Groupable {
    val groupId: String
}

interface CommitDao {
    suspend fun get(): List<Commit>
}