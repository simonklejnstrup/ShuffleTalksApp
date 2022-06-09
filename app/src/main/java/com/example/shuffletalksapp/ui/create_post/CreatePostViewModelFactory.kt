package com.example.shuffletalksapp.ui.create_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shuffletalksapp.repository.Repository

class CreatePostViewModelFactory(
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePostViewModel(repository) as T
    }
}