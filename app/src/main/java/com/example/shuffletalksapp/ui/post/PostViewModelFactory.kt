package com.example.shuffletalksapp.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shuffletalksapp.repository.Repository

class PostViewModelFactory(
    private val repository: Repository,
    private val postId: String
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostViewModel(repository, postId) as T
    }
}