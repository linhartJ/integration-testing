package itests

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.reflect.KClass

open class IntegrationTest(ctxClass: KClass<*>) {

    val appContext = AnnotationConfigApplicationContext(ctxClass.java)

    inline fun <reified T> bean(name: String): T {
        return appContext.getBean(name) as T
    }

}
