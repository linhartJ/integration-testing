package report

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import report.data.GoodReviewBalanceType
import report.data.ReportType
import report.factory.TestReportRuleFactory
import report.model.CrReporter
import report.model.PraiseDevThatHasEnoughReviewsRule
import tool.IntegrationTest
import tool.dao.TestCommitProvider
import tool.data.Commit

class CrReporterTest : IntegrationTest(TestReportConfiguration::class) {

    val reporter = bean<CrReporter>("crReporter")
    val commitDao = bean<TestCommitProvider>("testCommitProvider")
    val ruleFactory = bean<TestReportRuleFactory>("testReportRuleFactory")

    @Test
    fun `composeReports PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS & AT_LEAST_SOME_REVIEWED works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS)
        ruleFactory.goodReviewBalanceType = GoodReviewBalanceType.AT_LEAST_SOME_REVIEWED
        commitDao.commits = listOf(
                Commit("batman", "ISC-2 stuff CR: flash"),
                Commit("batman", "ISC-2 stuff CR: batman"),
                Commit("flash", "ISC-3 stuff"),
                Commit("superman", "ISC-1 stuff CR: batman"),
                Commit("superman", "ISC-1 stuff CR: wonderwoman"),
                Commit("superman", "ISC-4 stuff")
        )

        // when
        val result = reporter.composeReports()

        // then
        val expectedMessage = PraiseDevThatHasEnoughReviewsRule.GOOD_DEV_MESSAGE
        assertTrue(result.find { it.developer == "batman" } != null)
        assertTrue(result.find { it.developer == "flash" } == null)
        assertTrue(result.find { it.developer == "superman" } != null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

}