package com.example.shuffletalksapp.ui.feed

import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.model.Post
import com.example.shuffletalksapp.repository.Repository

class FeedViewModel(private val repository: Repository): ViewModel() {

    var feedCardItems = repository.getAllPosts().map { post -> toFeedCardItem(post) }

    fun toFeedCardItem(post: Post): FeedItemUIModel {

        val user = repository.getUser(post.comments[0].userid)

        var feedCardItem = FeedItemUIModel(
            user.avatar,
            user.username,
            user.postcount,
            post.comments[0].createdAt,
            post.comments[0].content,
            post.comments.size,
            post.postId
        )
        return feedCardItem
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