package tool.model

import org.springframework.stereotype.Component
import tool.data.Commit
import tool.data.ReviewedCommit

@Component
class CommitParser {

    fun parseReviewedCommit(commit: Commit): ReviewedCommit? {
        val reviewAuthor = parseReviewAuthor(commit.commitMessage)
        return reviewAuthor?.let { ReviewedCommit(commit.author, it) }
    }

    private fun parseReviewAuthor(commitMessage: String): String? {
        return if (commitMessage.contains("CR: ")) {
            commitMessage.substringAfter("CR: ")
        } else {
            null
        }
    }
}