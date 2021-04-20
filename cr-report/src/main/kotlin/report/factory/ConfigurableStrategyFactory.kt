package report.factory

import report.data.GoodReviewBalanceStrategy
import report.data.GoodReviewBalanceType
import report.data.GoodReviewerStrategy
import report.data.GoodReviewerType
import report.model.Above80PercentGoodReviewStrategy
import report.model.AtLeastSomeCommitsAreReviewedStrategy
import report.model.MostReviewsStrategy
import report.model.SomeReviewsStrategy

class ConfigurableStrategyFactory(
    val goodReviewBalanceType: GoodReviewBalanceType,
    val goodReviewerType: GoodReviewerType
) {

    fun goodReviewBalanceStrategy(): GoodReviewBalanceStrategy {
        return when (goodReviewBalanceType) {
            GoodReviewBalanceType.ABOVE_80_PERCENT -> Above80PercentGoodReviewStrategy()
            GoodReviewBalanceType.AT_LEAST_SOME_REVIEWED -> AtLeastSomeCommitsAreReviewedStrategy()
        }
    }

    fun goodReviewerStrategy(): GoodReviewerStrategy {
        return when (goodReviewerType) {
            GoodReviewerType.MOST_REVIEWS -> MostReviewsStrategy()
            GoodReviewerType.SOME_REVIEWS -> SomeReviewsStrategy()
        }
    }
}
