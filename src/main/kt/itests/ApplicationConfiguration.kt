@file:Suppress("SpringFacetCodeInspection")

package itests

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DaoConfiguration::class, ModelConfiguration::class)
class ApplicationConfiguration

@Configuration
@ComponentScan(basePackages = arrayOf("itests.dao"))
class DaoConfiguration

@Configuration
@ComponentScan(basePackages = arrayOf("itests.model"))
class ModelConfiguration




