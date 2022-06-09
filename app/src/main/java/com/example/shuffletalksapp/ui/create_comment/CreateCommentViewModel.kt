package com.example.shuffletalksapp.ui.create_comment

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.model.Post
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.ui.feed.FeedRecyclerViewAdapter
import com.example.shuffletalksapp.ui.feed.FeedViewModel

class CreateCommentViewModel(private val repository: Repository, private val postId: String): ViewModel() {

    private val sessionManager = SessionManager()

    fun createComment(content: String) {
        val username = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERNAME)
        if (username != null) {
            repository.createComment(content, username, postId)

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