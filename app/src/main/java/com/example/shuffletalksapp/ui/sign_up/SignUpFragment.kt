package com.example.shuffletalksapp.ui.sign_up

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentSignUpBinding
import com.example.shuffletalksapp.model.User
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.util.Constants

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentSignUpBinding.inflate(inflater)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val firstname = binding.firstnameEditText.text.toString()
            val lastname = binding.lastnameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val city = binding.cityEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val dbResponse = viewModel.createUser(username, firstname, lastname, email, city, password)

            when (dbResponse) {

                Constants.USERNAME_ALREADY_IN_USE_STRING ->
                    Toast.makeText(view.context, "Bruger allerede i brug", Toast.LENGTH_SHORT).show()

                Constants.EMAIL_ALREADY_IN_USE_STRING ->
                    Toast.makeText(view.context, "Email allerede i brug", Toast.LENGTH_SHORT).show()

                Constants.SUCCES_STRING -> {
                    Toast.makeText(view.context, "Bruger $username oprettet", Toast.LENGTH_SHORT).show()
                    navigateToLoginWithDelay(username)
                }
            }
        }
    }

    private fun navigateToLoginWithDelay(username: String) {

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.loginFragment)
        }, 2000)
    }
}
