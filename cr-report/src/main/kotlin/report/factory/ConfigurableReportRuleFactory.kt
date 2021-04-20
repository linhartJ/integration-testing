package report.factory

import report.data.GoodReviewBalanceStrategy
import report.data.GoodReviewerStrategy
import report.data.ReportRule
import report.data.ReportRuleFactory
import report.data.ReportType
import report.model.PraiseDevThatHasEnoughReviewsRule
import report.model.PraiseDevThatReviewLotOfCode
import report.model.ScoldDevThatDoesNotHaveEnoughReviewsRule

class ConfigurableReportRuleFactory(
    private val activeRules: List<ReportType>,
    private val goodReviewBalanceStrategy: GoodReviewBalanceStrategy,
    private val goodReviewerStrategy: GoodReviewerStrategy
) : ReportRuleFactory {

    override fun getAllReportsRule(): ReportRule {
        return newReportRule(activeRules.first())
    }

    private fun newReportRule(rule: ReportType): ReportRule {
        return when (rule) {
            ReportType.PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS -> PraiseDevThatHasEnoughReviewsRule(goodReviewBalanceStrategy)
            ReportType.SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS -> ScoldDevThatDoesNotHaveEnoughReviewsRule(
                goodReviewBalanceStrategy
            )
            ReportType.PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE -> PraiseDevThatReviewLotOfCode(goodReviewerStrategy)
        }
    }
}
