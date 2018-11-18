package itests

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.concurrent.thread

fun main() {
    val ctx = AnnotationConfigApplicationContext(ApplicationConfiguration::class.java)
    val calculator = ctx.getBean("crTool") as CrTool
    val resolveCommitsThread = thread {
        println(calculator.resolveCommitStats())
    }
    val printTimesMessageJob = thread {
        while (resolveCommitsThread.isAlive) {
            println("I'm fetching commits... ")
            Thread.sleep(1000)
        }
    }
    printTimesMessageJob.join()
}