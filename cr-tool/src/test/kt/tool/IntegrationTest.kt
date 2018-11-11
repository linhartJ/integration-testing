package tool

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.reflect.KClass

open class IntegrationTest(ctx: KClass<*>) {

    val ctx = AnnotationConfigApplicationContext(ctx.java)

    inline fun <reified T> bean(name: String): T {
        return ctx.getBean(name) as T
    }

}