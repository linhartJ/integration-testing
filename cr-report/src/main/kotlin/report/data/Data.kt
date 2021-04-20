package report.data

import tool.data.CommitStatistics

interface GoodReviewBalanceStrategy {
    fun hasGoodReviewBalance(commitAuthor: String, stats: CommitStatistics): Boolean
}

interface GoodReviewerStrategy {
    fun isGoodReviewer(reviewAuthor: String, stats: CommitStatistics): Boolean
    fun getGoodReviewerMessage(): String
}

interface ReportRule {
    fun apply(stats: CommitStatistics): List<Report>
}

interface ReportRuleFactory {
    fun getAllReportsRule(): ReportRule
}

data class Report(val developer: String, val message: String)

enum class ReportType {
    PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS,
    SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS,
    PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE
}

enum class GoodReviewBalanceType {
    ABOVE_80_PERCENT,
    AT_LEAST_SOME_REVIEWED
}

enum class GoodReviewerType {
    MOST_REVIEWS,
    SOME_REVIEWS
}
