package com.example.shuffletalksapp.ui.create_post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.model.Post
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.ui.feed.FeedRecyclerViewAdapter
import com.example.shuffletalksapp.ui.feed.FeedViewModel

class CreatePostViewModel(private val repository: Repository): ViewModel() {

    private val sessionManager = SessionManager()

    @RequiresApi(Build.VERSION_CODES.O)
    fun createPost(content: String) {
        val username = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERNAME)
        if (username != null) {
            repository.createPost(content, username)

        }
    }



















    // For use with retrofit
    /*
    val response: MutableLiveData<Response<Post>> = MutableLiveData() // For use with retrofit

    fun getPost(post: Post): Post {

        // For use with retrofit
        viewModelScope.launch {
            Log.d("taggy", "Nået til 21 i FeedViewModel")
            val _response = repository.getPost(post)
            response.value = _response
        }

    }

    fun getAllPosts() {

        // For use with retrofit
        viewModelScope.launch {
            val _response = repository.getAllPosts()
            response.value = _response
        }
    }

     */
}