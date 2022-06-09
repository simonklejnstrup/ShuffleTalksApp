package com.example.shuffletalksapp.ui.post

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentPostBinding
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants

class PostFragment : Fragment(R.layout.fragment_post) {

    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory(Repository(), postId)
    }

    val args: PostFragmentArgs by navArgs()
    private val sessionManager = SessionManager()

    private lateinit var postId: String
    private lateinit var binding: FragmentPostBinding

    @RequiresApi(Build.VERSION_CODES.N)
    private val adapter = PostRecyclerViewAdapter(
        { commentId, userId ->
            if (userId != Constants.NO_USER_STRING) {
                viewModel.updateLike(
                    commentId, Like(
                        sessionManager.getUserDetails().get(sessionManager.KEY_USERID)!!,
                        sessionManager.getUserDetails().get(sessionManager.KEY_USERNAME)!!
                    )
                )

            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        },
        {
            // TODO: navigate to create comment with quote
        },
        {
            // TODO: On avatar clicked
                username ->
            val action = PostFragmentDirections.actionPostFragmentToProfileFragment(username)
            findNavController().navigate(action)

        },
        {
            // onPostUpdated
                newContent, commentId ->
            viewModel.updateComment(newContent, commentId)
        },
        sessionManager.getUserDetails().get(sessionManager.KEY_USERID) ?: Constants.NO_USER_STRING
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentPostBinding.inflate(inflater)
        }
        postId = args.postId
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContent()

        binding.fab.setOnClickListener {
            if (sessionManager.isLoggedIn()) {
                val action =
                    PostFragmentDirections.actionPostFragmentToCreateCommentFragment(postId)
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.feedFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initContent() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
        adapter.setData(viewModel.comments)
    }
}