@file:Suppress("SpringFacetCodeInspection")

package tool

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DaoConfiguration::class, ModelConfiguration::class)
class ToolConfiguration

@Configuration
@ComponentScan(basePackages = arrayOf("tool.dao"))
class DaoConfiguration

@Configuration
@ComponentScan(basePackages = arrayOf("tool.model"))
class ModelConfiguration
