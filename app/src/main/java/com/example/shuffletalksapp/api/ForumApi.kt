package com.example.shuffletalksapp.api

import com.example.shuffletalksapp.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body

interface ForumApi {

    @GET("post")
    suspend fun getPost(@Body post: Post): Response<Post>

    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>


}