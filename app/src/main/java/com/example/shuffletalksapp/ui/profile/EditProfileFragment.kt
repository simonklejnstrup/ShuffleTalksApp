package com.example.shuffletalksapp.ui.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentEditProfileBinding
import com.example.shuffletalksapp.databinding.FragmentProfileBinding
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager

class EditProfileFragment: Fragment(R.layout.fragment_edit_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(Repository())
    }

    private val sessionManager = SessionManager()
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentEditProfileBinding.inflate(inflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = sessionManager.getUserDetails().get(sessionManager.KEY_USERNAME)
        val user = username?.let { viewModel.getUser(it) }

        binding.apply {

            firstnameEditText.setText(user?.firstname)
            lastnameEditText.setText(user?.lastname)
            emailEditText.setText(user?.email)
            Glide.with(view)
                .load(user?.avatar)
                .into(binding.avatarRoundedImageView)

        }

        initOnClickListeners()


    }

    private fun initOnClickListeners() {
        binding.apply {
            fab.setOnClickListener {

                val newFirstname = binding.firstnameEditText.text.toString().trim()
                val newLastname = binding.lastnameEditText.text.toString().trim()
                val newEmail  = binding.emailEditText.text.toString().trim()

                viewModel.updateUser(newFirstname, newLastname, newEmail)

                Toast.makeText(view?.context, "Bruger opdateret", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.profileFragment)
                }, 2000)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }

    }
}