package report

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import report.data.GoodReviewBalanceType
import report.data.GoodReviewerType
import report.data.ReportType
import report.factory.TestReportRuleFactory
import report.model.CrReporter
import report.model.MostReviewsStrategy
import report.model.PraiseDevThatHasEnoughReviewsRule
import tool.ModelConfiguration
import tool.TestDaoConfiguration
import report.model.ScoldDevThatDoesNotHaveEnoughReviewsRule
import report.model.SomeReviewsStrategy
import tool.dao.TestCommitProvider
import tool.data.Commit

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [
    TestDaoConfiguration::class,
    ModelConfiguration::class,
    ReportModelConfiguration::class,
    TestReportFactoryConfiguration::class
])
class CrReporterTest {

    @Autowired
    private lateinit var reporter: CrReporter
    @Autowired
    private lateinit var commitDao: TestCommitProvider
    @Autowired
    private lateinit var ruleFactory: TestReportRuleFactory

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

    @Test
    fun `composeReports PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS & ABOVE_80_PERCENT works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS)
        ruleFactory.goodReviewBalanceType = GoodReviewBalanceType.ABOVE_80_PERCENT
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
        assertTrue(result.find { it.developer == "superman" } == null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

    @Test
    fun `composeReports SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS & ABOVE_80_PERCENT works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS)
        ruleFactory.goodReviewBalanceType = GoodReviewBalanceType.ABOVE_80_PERCENT
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
        val expectedMessage = ScoldDevThatDoesNotHaveEnoughReviewsRule.BAD_DEV_MESSAGE
        assertTrue(result.find { it.developer == "batman" } == null)
        assertTrue(result.find { it.developer == "flash" } != null)
        assertTrue(result.find { it.developer == "superman" } != null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

    @Test
    fun `composeReports SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS & AT_LEAST_SOME_REVIEWED works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS)
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
        val expectedMessage = ScoldDevThatDoesNotHaveEnoughReviewsRule.BAD_DEV_MESSAGE
        assertTrue(result.find { it.developer == "batman" } == null)
        assertTrue(result.find { it.developer == "flash" } != null)
        assertTrue(result.find { it.developer == "superman" } == null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

    @Test
    fun `composeReports PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE & MOST_REVIEWS works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE)
        ruleFactory.goodReviewerType = GoodReviewerType.MOST_REVIEWS
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
        val expectedMessage = MostReviewsStrategy.GOOD_REVIEWER_MESSAGE
        assertTrue(result.find { it.developer == "batman" } != null)
        assertTrue(result.find { it.developer == "flash" } == null)
        assertTrue(result.find { it.developer == "wonderwoman" } == null)
        assertTrue(result.find { it.developer == "superman" } == null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

    @Test
    fun `composeReports PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE & SOME_REVIEWS works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE)
        ruleFactory.goodReviewerType = GoodReviewerType.SOME_REVIEWS
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
        val expectedMessage = SomeReviewsStrategy.GOOD_REVIEWER_MESSAGE
        assertTrue(result.find { it.developer == "batman" } != null)
        assertTrue(result.find { it.developer == "flash" } != null)
        assertTrue(result.find { it.developer == "wonderwoman" } != null)
        assertTrue(result.find { it.developer == "superman" } == null)
        result.forEach {
            assertEquals(expectedMessage, it.message)
        }
    }

    @Test
    fun `composeReports multiple active rules works`() {
        // given
        ruleFactory.activeRules = listOf(ReportType.PRAISE_DEV_THAT_REVIEWS_A_LOT_OF_CODE, ReportType.SCOLD_DEV_THAT_DOES_NOT_HAVE_ENOUGH_REVIEWS)
        ruleFactory.goodReviewerType = GoodReviewerType.MOST_REVIEWS
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
        val messageForReviewer = MostReviewsStrategy.GOOD_REVIEWER_MESSAGE
        val messageForCommitAuthor = ScoldDevThatDoesNotHaveEnoughReviewsRule.BAD_DEV_MESSAGE

        assertTrue(result.find { it.developer == "batman" && it.message == messageForReviewer } != null)
        assertTrue(result.find { it.developer == "flash" && it.message == messageForCommitAuthor } != null)
    }

}