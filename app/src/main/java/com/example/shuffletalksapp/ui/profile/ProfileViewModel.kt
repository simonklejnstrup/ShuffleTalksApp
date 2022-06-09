package com.example.shuffletalksapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.model.User
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager

class ProfileViewModel(private val repository: Repository): ViewModel() {

    private val sessionManager = SessionManager()



    private val username = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERNAME)
    private val userId = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERID)

    val user = username?.let { repository.getUserByUsername(it) }



    val userUIModel = user?.let { toProfileUIModel(it) }

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

    fun updateUser(newFirstname: String, newLastname: String, newEmail: String) {
        if (userId != null) {
            repository.updateUser(userId, newFirstname, newLastname, newEmail)
        }
    }
}