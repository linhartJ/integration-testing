package report.model

import org.springframework.stereotype.Component
import report.data.Report
import report.data.ReportRuleFactory
import tool.model.CrTool

@Component
class CrReporter(private val tool: CrTool,
                 reportRuleFactory: ReportRuleFactory) {

    private val reportRule = reportRuleFactory.getAllReportsRule()

    fun composeReports(): List<Report> {
        val stats = tool.resolveCommitStats()
        return reportRule.apply(stats)
    }

}