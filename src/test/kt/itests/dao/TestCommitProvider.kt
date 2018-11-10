package itests.dao

import itests.data.Commit
import itests.data.CommitDao

class TestCommitProvider : CommitDao {

    lateinit var commits: List<Commit>

    override fun get(): List<Commit> {
        return commits
    }
}