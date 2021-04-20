package tool.dao

import tool.data.Commit
import tool.data.CommitDao

class TestCommitProvider : CommitDao {

    lateinit var commits: List<Commit>

    override fun get(): List<Commit> {
        return commits
    }
}