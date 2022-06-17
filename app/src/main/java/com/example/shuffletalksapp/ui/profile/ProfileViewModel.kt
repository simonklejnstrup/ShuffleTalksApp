package com.example.shuffletalksapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.model.User
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager

class ProfileViewModel(private val repository: Repository): ViewModel() {

    private val sessionManager = SessionManager()
    private val userId = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERID)


    fun toProfileUIModel(user: User): ProfileUIModel {

        return ProfileUIModel(
            user.avatar,
            user.postcount,
            user.firstname,
            user.lastname,
            user.username,
            user.city,
            user.email
        )
    }

    fun getUser(username: String): ProfileUIModel {
        val user = repository.getUserByUsername(username)
        if (user != null) {
            return toProfileUIModel(user)
        }
        return ProfileUIModel()
    }


    fun updateUser(newFirstname: String, newLastname: String, newEmail: String) {
        if (userId != null) {
            repository.updateUser(userId, newFirstname, newLastname, newEmail)
        }
    }
}