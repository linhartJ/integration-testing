package report.model

import report.data.GoodReviewerStrategy
import tool.data.CommitStatistics

class SomeReviewsStrategy : GoodReviewerStrategy {

    companion object {
        const val GOOD_REVIEWER_MESSAGE = "Ok, ok, you asked for code review. Good job, I guess."
    }

    override fun isGoodReviewer(reviewAuthor: String, stats: CommitStatistics): Boolean {
        return stats.reviewsByAuthor
                .filter { (author, reviewsCount) -> author == reviewAuthor && reviewsCount > 0 }
                .isNotEmpty()
    }

    override fun getGoodReviewerMessage(): String {
        return GOOD_REVIEWER_MESSAGE
    }
}
