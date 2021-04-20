package report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import report.factory.TestReportRuleFactory

@Configuration
class TestReportFactoryConfiguration {

    @Bean
    fun testReportRuleFactory(): TestReportRuleFactory {
        return TestReportRuleFactory()
    }
}