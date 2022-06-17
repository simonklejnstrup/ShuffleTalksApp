package com.example.shuffletalksapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.shuffletalksapp.api.Api
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.model.Post
import com.example.shuffletalksapp.model.Quote
import com.example.shuffletalksapp.model.User

class Repository {

    fun getAllPosts(): MutableList<Post> {
        return Api.getAllPosts()
    }

    fun getPost(postId: String): Post {
        return Api.getOnePost(postId)
    }

    fun getUser(userId: String): User {
        return Api.getOneUser(userId)
    }

    fun getUserByUsername(username: String): User? {
        return Api.getUserByUsername(username)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createPost(content: String, username: String) {
        Api.createPost(content, username)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createComment(content: String, username: String, postId: String, quotes: Array<Quote>?) {
        Api.createComment(content, username, postId, quotes)
    }

    fun createUser(user: User): String {
        return Api.createUser(user)
    }

    fun updateUser(userId: String, newFirstname: String, newLastname: String, newEmail: String) {
        Api.updateUser(userId, newFirstname, newLastname, newEmail)
    }

    fun updateComment(newContent: String, commentId: String, postId: String) {
        Api.updateComment(newContent, commentId, postId)
    }

    fun updateLikes(commentId: String, postid: String, like: Like): Int {
        return Api.updateLikes(commentId, postid, like)
    }


    // FOR USE WITH RETROFIT
    /*suspend fun getPost(post: Post): Response<Post> {
        return RetrofitInstance.api.getPost(post)
    }

    suspend fun getAllPosts(): Response<List<Post>> {
        return RetrofitInstance.api.getAllPosts()
    }*/
}