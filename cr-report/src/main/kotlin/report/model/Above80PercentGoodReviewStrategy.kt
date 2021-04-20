package report.model

import report.data.GoodReviewBalanceStrategy
import tool.data.CommitStatistics

class Above80PercentGoodReviewStrategy : GoodReviewBalanceStrategy {

    override fun hasGoodReviewBalance(commitAuthor: String, stats: CommitStatistics): Boolean {
        val allCommitsOfAuthor = stats.commitsByAuthor[commitAuthor]
            ?: throw IllegalStateException("Cannot determine if '$commitAuthor' has good review balance. no commits found")
        val reviewedCommitsByAuthor = stats.reviewedCommitsByAuthor[commitAuthor] ?: 0

        return (reviewedCommitsByAuthor.toDouble() / allCommitsOfAuthor.toDouble()) > 0.8
    }
}
