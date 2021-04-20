@file:Suppress("SpringFacetCodeInspection")

package itests

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    DaoConfiguration::class,
    ModelConfiguration::class
)
class ApplicationConfiguration

@ComponentScan("itests.dao")
class DaoConfiguration

@ComponentScan("itests.model")
class ModelConfiguration
