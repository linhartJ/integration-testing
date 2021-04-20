package tool.model

import tool.data.GroupResolver
import tool.data.CommitDao
import tool.data.CommitStatistics
import org.springframework.stereotype.Component
import tool.data.ReviewedCommit

@Component
class CrTool(private val commitsDao: CommitDao,
             private val commitParser: CommitParser) {

    fun resolveCommitStats(): CommitStatistics {
        val allCommits = commitsDao.get()
        val reviews = allCommits.mapNotNull { commitParser.parseReviewedCommit(it) }
        val reviewedCommits = reviews.map { ReviewedCommit(it.reviewAuthor, it.commitAuthor) }

        return CommitStatistics(
                commitsWithoutReview = allCommits.size - reviews.size,
                commitsWithReview = reviews.size,
                commitsByAuthor = GroupResolver(allCommits).countItemsGroups(),
                reviewedCommitsByAuthor = GroupResolver(reviewedCommits).countItemsGroups(),
                reviewsByAuthor = GroupResolver(reviews).countItemsGroups()
        )
    }

}