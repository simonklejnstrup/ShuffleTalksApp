package com.example.shuffletalksapp.ui.sign_up

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.model.User

import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants
import java.time.LocalDate
import java.util.*

class SignUpViewModel(private val repository: Repository): ViewModel() {


    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser(username: String,
                   firstname: String,
                   lastname: String,
                   email: String,
                   city: String,
                   password: String): String {
        val user = User (
            UUID.randomUUID().toString(),
            username,
            LocalDate.now().toString(),
            firstname,
            lastname,
            city,
            email,
            password,
            0,
            R.drawable.icon_app_round
        )

        return repository.createUser(user)

    }
}