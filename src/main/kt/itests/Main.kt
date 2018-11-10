package itests

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() = runBlocking {
    val ctx = AnnotationConfigApplicationContext(IntegrationTestingConfiguration::class.java)
    val calculator = ctx.getBean("crTool") as CrTool
    val resolveCommitsJob = launch {
        println(calculator.resolveCommitStats())
    }
    val printTimesMessageJob = launch {
        while (resolveCommitsJob.isActive) {
            println("I'm fetching commits... ")
            delay(1000)
        }
    }
    printTimesMessageJob.join()
}