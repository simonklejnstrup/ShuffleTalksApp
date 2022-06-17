package com.example.shuffletalksapp.ui.create_comment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.model.Quote
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager

class CreateCommentViewModel(private val repository: Repository, private val postId: String): ViewModel() {

    private val sessionManager = SessionManager()

    @RequiresApi(Build.VERSION_CODES.O)
    fun createComment(content: String, quotes: Array<Quote>?) {
        val username = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERNAME)
        if (username != null) {
            repository.createComment(content, username, postId, quotes)

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