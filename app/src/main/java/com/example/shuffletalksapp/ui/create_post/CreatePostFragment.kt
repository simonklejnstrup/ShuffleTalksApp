package com.example.shuffletalksapp.ui.create_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentCreatePostBinding
import com.example.shuffletalksapp.repository.Repository


class CreatePostFragment: Fragment(R.layout.fragment_create_post) {
    private val viewModel: CreatePostViewModel by viewModels {
        CreatePostViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentCreatePostBinding.inflate(inflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            if (binding.editText.text.trim().isEmpty()) {
                Toast.makeText(context, "Indhold ikke udfyldt", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createPost(binding.editText.text.toString())
                findNavController().navigate(R.id.feedFragment)

            }
        }
    }



}
