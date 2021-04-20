package report.factory

import report.data.GoodReviewBalanceType
import report.data.GoodReviewerType
import report.data.Report
import report.data.ReportRule
import report.data.ReportRuleFactory
import report.data.ReportType
import tool.data.CommitStatistics

class TestReportRuleFactory : ReportRuleFactory {

    lateinit var activeRules: List<ReportType>
    var goodReviewBalanceType: GoodReviewBalanceType? = null
    var goodReviewerType: GoodReviewerType? = null

    override fun getAllReportsRule(): ReportRule {
        return LazyReportRule()
    }

    inner class LazyReportRule : ReportRule {
        override fun apply(stats: CommitStatistics): List<Report> {
            val strategyFactory = ConfigurableStrategyFactory(
                goodReviewBalanceType ?: GoodReviewBalanceType.ABOVE_80_PERCENT,
                goodReviewerType ?: GoodReviewerType.MOST_REVIEWS
            )

            return ConfigurableReportRuleFactory(
                activeRules,
                strategyFactory.goodReviewBalanceStrategy(),
                strategyFactory.goodReviewerStrategy()
            ).getAllReportsRule().apply(stats)
        }
    }
}
