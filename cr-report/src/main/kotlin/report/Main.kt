package report

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import report.model.CrReporter

fun main() {
    val ctx = AnnotationConfigApplicationContext(ReportConfiguration::class.java)
    val reporter = ctx.getBean("crReporter") as CrReporter
    reporter.composeReports()
        .forEach {
            println("${it.developer.padEnd(15, ' ')} -> ${it.message}")
        }
}
