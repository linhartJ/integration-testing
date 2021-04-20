package tool

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tool.dao.TestCommitProvider

@Configuration
class TestDaoConfiguration {

    @Bean
    fun testCommitProvider(): TestCommitProvider {
        return TestCommitProvider()
    }

}