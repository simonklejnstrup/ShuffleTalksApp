package com.example.shuffletalksapp.ui.profile

import android.os.Bundle
import android.util.Log
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

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentProfileBinding
    private val sessionManager = SessionManager()
    val args: ProfileFragmentArgs by navArgs()


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

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        Log.d("args.isCurrentUser ", args.isCurrentUser.toString())
        Log.d("session user ", sessionManager.getUserDetails().get(sessionManager.KEY_USERNAME)!!)
        if (args.isCurrentUser) {
            setupFragmentForCurrentUser(view)
        } else {
            setupFragmentForOtherUser(view)
        }




    }

    private fun setupFragmentForOtherUser(view: View) {

        val user: ProfileUIModel? =
            args.username?.let { viewModel.getUser(it) }

        binding.apply {

            firstnameTextView.text = user?.firstname
            lastnameTextView.text = user?.lastname
            usernameTextView.text = user?.username
            postcountTextView.text = user?.postcount.toString()
            emailTextView.text = user?.email

            Glide.with(view)
                .load(user?.avatar)
                .into(binding.avatarRoundedImageView)

            btnLogout.visibility = View.INVISIBLE

            fab.visibility = View.INVISIBLE
        }
    }

    private fun setupFragmentForCurrentUser(view: View) {

        val user: ProfileUIModel =
            viewModel.getUser(sessionManager.getUserDetails().get(sessionManager.KEY_USERNAME)!!)

        binding.apply {

            firstnameTextView.text = user.firstname
            lastnameTextView.text = user.lastname
            usernameTextView.text = user.username
            postcountTextView.text = user.postcount.toString()
            emailTextView.text = user.email

            Glide.with(view)
                .load(user.avatar)
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