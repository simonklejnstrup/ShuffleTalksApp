package com.example.shuffletalksapp.ui.login

import androidx.lifecycle.ViewModel

import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants

class LoginViewModel(private val repository: Repository): ViewModel() {

    private val sessionManager = SessionManager()

    fun validate(username: String, password: String): String {
        val user = repository.getUserByUsername(username)

        if (user == null) {
            return Constants.NO_USER_STRING
        }
        if (user.password.equals(password)) {
            sessionManager.createSession(username, user.userId)
            return Constants.SUCCES_STRING
        }
        else {
            return Constants.WRONG_PASS_STRING
        }

    }
}