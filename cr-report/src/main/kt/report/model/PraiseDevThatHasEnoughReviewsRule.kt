package report.model

import report.data.GoodReviewBalanceStrategy
import report.data.Report
import report.data.ReportRule
import tool.data.CommitStatistics

class PraiseDevThatHasEnoughReviewsRule(val strategy: GoodReviewBalanceStrategy): ReportRule {

     companion object {
         const val GOOD_DEV_MESSAGE = "Good job on asking for code review."
     }

    override fun apply(stats: CommitStatistics): List<Report> {
        return stats.commitsByAuthor
                .filter { (author, _) -> strategy.hasGoodReviewBalance(author, stats) }
                .map { (author, _) -> newReport(author) }
    }

    private fun newReport(author: String): Report {
        return Report(author, GOOD_DEV_MESSAGE)
    }
}