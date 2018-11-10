package itests

import itests.dao.TestCommitProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Scope

@Configuration
@Import(ModelConfiguration::class, TestDaoConfiguration::class)
class TestApplicationConfiguration {
}

@Configuration
class TestDaoConfiguration {

    @Bean
    @Scope("singleton")
    fun testCommitProvider(): TestCommitProvider {
        return TestCommitProvider()
    }

}