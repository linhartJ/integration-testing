package itests

import org.springframework.stereotype.Component

@Component
class CrTool(
    private val commitsDao: CommitDao,
    private val commitParser: CommitParser
) {

    fun resolveCommitStats(): CommitStatistics {
        val allCommits = commitsDao.get()
        val reviewedCommits = allCommits.mapNotNull { commitParser.parseReviewedCommit(it) }

        return CommitStatistics(
            commitsWithoutReview = allCommits.size - reviewedCommits.size,
            commitsWithReview = reviewedCommits.size,
            commitsByAuthor = GroupResolver(allCommits).countItemsGroups(),
            reviewsByAuthor = GroupResolver(reviewedCommits).countItemsGroups()
        )
    }
}
