package itests

import itests.model.CrTool
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.concurrent.thread

fun main()  {
    val ctx = AnnotationConfigApplicationContext(ApplicationConfiguration::class.java)
    val calculator = ctx.getBean("crTool") as CrTool
    val resolveCommitsJob = thread {
        println(calculator.resolveCommitStats())
    }
    val printTimesMessageJob = thread {
        while (resolveCommitsJob.isAlive) {
            println("I'm fetching commits... ")
            Thread.sleep(1000)
        }
    }
    printTimesMessageJob.join()
}