package report.model

import report.data.GoodReviewBalanceStrategy
import report.data.Report
import report.data.ReportRule
import tool.data.CommitStatistics

class ScoldDevThatDoesNotHaveEnoughReviewsRule(private val strategy: GoodReviewBalanceStrategy) : ReportRule {

    companion object {
        const val BAD_DEV_MESSAGE = "Hey, YOU. Ask for CR or I won't buy you beer at Kick-off"
    }

    override fun apply(stats: CommitStatistics): List<Report> {
        return stats.commitsByAuthor
                .filter { (author, _) -> !strategy.hasGoodReviewBalance(author, stats) }
                .map { (author, _) -> newReport(author) }
    }

    private fun newReport(author: String): Report {
        return Report(author, BAD_DEV_MESSAGE)
    }
}