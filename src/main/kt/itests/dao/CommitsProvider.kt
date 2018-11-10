package itests.dao

import itests.data.Commit
import itests.data.CommitDao
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class CommitsProvider : CommitDao {

    private val scalerDevs = listOf("l.najman", "m.horcicko", "j.fous", "o.sukala", "p.holubec", "j.linhart", "j.novak", "d.bydzovsky", "j.skorvanek")
    private val commitMessages = listOf("fixed stuff", "broke stuff", "implemented stuff", "tried some stuff", "refactored some stuff")

    override suspend fun get(): List<Commit> {
        delay(10_000) // it takes a long time to get commits from git
        return if (gitServiceIsAvailable()) {
            retrieveCommits()
        } else {
            emptyList()
        }

    }

    private fun retrieveCommits(): List<Commit> {
        return (1..100).map { aCommit() }
    }

    private fun gitServiceIsAvailable(): Boolean {
        return Math.random() > 0.2 // once in 5 tries (roughly) this call returns false, hups
    }


    private val randomDev get() = scalerDevs.random()
    private val randomNumber get() = (Math.random() * 1000).toInt()
    private val randomMessage get() = commitMessages.random()
    private val hadCR get() = (Math.random() > 0.1)

    private fun aCommit(): Commit {
        return Commit(randomDev, "ISC-$randomNumber $randomMessage ${if (hadCR) "CR: $randomDev" else ""}")
    }

}