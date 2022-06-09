package com.example.shuffletalksapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentProfileBinding
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.ui.MainActivity
import com.example.shuffletalksapp.ui.post.PostFragmentArgs
import com.example.shuffletalksapp.util.Constants

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentProfileBinding
    private val sessionManager = SessionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentProfileBinding.inflate(inflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.apply {
            firstnameTextView.text = viewModel.userUIModel?.firstname
            lastnameTextView.text = viewModel.userUIModel?.lastname
            usernameTextView.text = viewModel.userUIModel?.username
            postcountTextView.text = viewModel.userUIModel?.postcount.toString()
            emailTextView.text = viewModel.userUIModel?.email

            Glide.with(view)
                .load(viewModel.userUIModel?.avatar)
                .into(binding.avatarRoundedImageView)

            btnLogout.setOnClickListener {
                sessionManager.logoutUser()
                findNavController().navigate(R.id.loginFragment)
            }
            fab.setOnClickListener {
                findNavController().navigate(R.id.editProfileFragment)

            }
        }

    }
}