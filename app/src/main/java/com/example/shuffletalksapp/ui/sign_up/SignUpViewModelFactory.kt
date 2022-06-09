package com.example.shuffletalksapp.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shuffletalksapp.repository.Repository

class SignUpViewModelFactory(
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}