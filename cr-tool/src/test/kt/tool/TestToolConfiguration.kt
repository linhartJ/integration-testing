package tool

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import tool.dao.TestCommitProvider

@Configuration
@Import(ModelConfiguration::class, TestDaoConfiguration::class)
class TestToolConfiguration {
}

@Configuration
class TestDaoConfiguration {

    @Bean
    fun testCommitProvider(): TestCommitProvider {
        return TestCommitProvider()
    }

}