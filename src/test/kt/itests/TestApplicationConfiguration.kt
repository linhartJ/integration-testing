package itests

import itests.dao.TestCommitProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class TestDaoConfiguration {

    @Bean
    @Scope("singleton")
    fun testCommitProvider(): TestCommitProvider {
        return TestCommitProvider()
    }

}