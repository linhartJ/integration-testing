@file:Suppress("SpringFacetCodeInspection")

package tool

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    DaoConfiguration::class,
    ModelConfiguration::class
)
class ToolConfiguration

@ComponentScan("tool.dao")
class DaoConfiguration

@ComponentScan("tool.model")
class ModelConfiguration


