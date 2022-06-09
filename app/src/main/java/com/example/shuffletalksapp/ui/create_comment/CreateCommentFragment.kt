package com.example.shuffletalksapp.ui.create_comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentCreateCommentBinding
import com.example.shuffletalksapp.databinding.FragmentPostBinding
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.ui.feed.FeedFragmentDirections
import com.example.shuffletalksapp.ui.post.PostFragmentArgs
import com.example.shuffletalksapp.ui.post.PostRecyclerViewAdapter
import com.example.shuffletalksapp.ui.post.PostViewModel
import com.example.shuffletalksapp.ui.post.PostViewModelFactory

class CreateCommentFragment: Fragment(R.layout.fragment_create_comment) {

    private val viewModel: CreateCommentViewModel by viewModels {
        CreateCommentViewModelFactory(Repository(), postId)
    }

    val args: CreateCommentFragmentArgs by navArgs()
    private val sessionManager = SessionManager()

    private lateinit var postId: String
    private lateinit var binding: FragmentCreateCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentCreateCommentBinding.inflate(inflater)
        }
        postId = args.postId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            if (binding.editText.text.trim().isEmpty()) {
                Toast.makeText(context, "Indhold ikke udfyldt", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createComment(binding.editText.text.toString())
                val action = CreateCommentFragmentDirections.actionCreateCommentFragmentToPostFragment(postId)
                findNavController().navigate(action)
            }
        }
    }
}