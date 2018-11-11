package report.model

import report.data.GoodReviewerStrategy
import report.data.Report
import report.data.ReportRule
import tool.data.CommitStatistics

class PraiseDevThatReviewLotOfCode(private val strategy: GoodReviewerStrategy) : ReportRule {

    override fun apply(stats: CommitStatistics): List<Report> {
        return stats.reviewsByAuthor
                .filter { (author, _) -> strategy.isGoodReviewer(author, stats) }
                .map { (author, _) -> newReport(author) }
    }

    private fun newReport(author: String): Report {
        return Report(author, strategy.getGoodReviewerMessage())
    }
}