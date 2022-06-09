package com.example.shuffletalksapp.ui.create_comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shuffletalksapp.repository.Repository

class CreateCommentViewModelFactory(
    private val repository: Repository,
    private val postId: String
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateCommentViewModel(repository, postId) as T
    }
}