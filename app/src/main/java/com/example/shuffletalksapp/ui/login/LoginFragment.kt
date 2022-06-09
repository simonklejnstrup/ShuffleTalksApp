package com.example.shuffletalksapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentLoginBinding
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentLoginBinding.inflate(inflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val username = binding.usernameEditTextView.text.toString()
            val password = binding.passwordEditTextView.text.toString()
            val validationResponse = viewModel.validate(username, password)

            if (validationResponse.equals(Constants.SUCCES_STRING)) {
                findNavController().navigate(R.id.profileFragment)
            }
            if (validationResponse.equals(Constants.WRONG_PASS_STRING)) {
                Toast.makeText(context, R.string.wrong_password, Toast.LENGTH_SHORT).show()
            }
            if (validationResponse.equals(Constants.NO_USER_STRING)) {
                Toast.makeText(context, R.string.no_such_user, Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }

}
