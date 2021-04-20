@file:Suppress("SpringFacetCodeInspection")

package report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import report.data.GoodReviewBalanceType
import report.data.GoodReviewerType
import report.data.ReportType
import report.factory.ConfigurableReportRuleFactory
import report.factory.ConfigurableStrategyFactory
import tool.ToolConfiguration

@Configuration
@Import(
    ToolConfiguration::class,
    ReportModelConfiguration::class,
    ReportFactoryConfiguration::class
)
class ReportConfiguration

@ComponentScan("report.model")
class ReportModelConfiguration

class ReportFactoryConfiguration {

    @Bean
    fun reportRuleFactory(strategyFactory: ConfigurableStrategyFactory): ConfigurableReportRuleFactory {
        return ConfigurableReportRuleFactory(
            listOf(
                ReportType.PRAISE_DEV_THAT_HAS_ENOUGH_REVIEWS
            ),
            strategyFactory.goodReviewBalanceStrategy(),
            strategyFactory.goodReviewerStrategy()
        )
    }

    @Bean
    fun strategyFactory(): ConfigurableStrategyFactory {
        return ConfigurableStrategyFactory(
            GoodReviewBalanceType.ABOVE_80_PERCENT,
            GoodReviewerType.MOST_REVIEWS
        )
    }
}
