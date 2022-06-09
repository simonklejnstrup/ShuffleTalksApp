package com.example.shuffletalksapp.ui.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.model.Comment
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.model.Post
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.ui.feed.FeedItemUIModel

class PostViewModel(private val repository: Repository, postId: String): ViewModel() {

    val comments = repository.getPost(postId).comments.map { comment -> toPostItemUIModel(comment) }

    private val postId = postId


    fun toPostItemUIModel(comment: Comment): PostItemUIModel {

        val user = repository.getUser(comment.userid)

        val postItemUiModel = PostItemUIModel(
            user.username,
            user.userId,
            user.postcount,
            user.avatar,
            comment.createdAt,
            comment.content,
            comment.commentId,
            comment.quotes,
            comment.likes
        )
        return postItemUiModel
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateLike(commentId: String, _like: Like) {
        val comment = comments.find { comment -> comment.commentId == commentId}
        val isLikedByCurrentUser = comment?.likes?.any { like -> like.userId == _like.userId}
        if (isLikedByCurrentUser == true) {
            comment.likes.removeIf { it.userId == _like.userId }
        } else {
            comment?.likes?.add(_like)
        }
    }

    fun updateComment(newContent: String, commentId: String) {
        repository.updateComment(newContent, commentId, postId)
    }




}