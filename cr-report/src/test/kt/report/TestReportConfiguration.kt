package report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import report.factory.TestReportRuleFactory
import tool.TestToolConfiguration

@Configuration
@Import(
        TestToolConfiguration::class,
        ReportModelConfiguration::class,
        TestReportFactoryConfiguration::class
)
class TestReportConfiguration

@Configuration
class TestReportFactoryConfiguration {

    @Bean
    fun testReportRuleFactory(): TestReportRuleFactory {
        return TestReportRuleFactory()
    }
}