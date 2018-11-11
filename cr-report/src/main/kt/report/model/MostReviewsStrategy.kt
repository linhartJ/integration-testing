package report.model

import report.data.GoodReviewerStrategy
import tool.data.CommitStatistics

class MostReviewsStrategy : GoodReviewerStrategy {

    companion object {
        const val GOOD_REVIEWER_MESSAGE = "Well done! You have made most code reviews of all."
    }

    override fun isGoodReviewer(reviewAuthor: String, stats: CommitStatistics): Boolean {
        val maxReviewCount = stats.reviewsByAuthor.maxBy { (_, reviewsCount) -> reviewsCount }?.value ?: 1
        return stats.reviewsByAuthor
                .filter { (author, reviewsCount) -> author == reviewAuthor && reviewsCount == maxReviewCount }
                .isNotEmpty()

    }

    override fun getGoodReviewerMessage(): String {
        return GOOD_REVIEWER_MESSAGE
    }
}