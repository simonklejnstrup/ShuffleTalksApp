package com.example.shuffletalksapp.ui.post

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.shuffletalksapp.model.Quote
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
        {   // onLikeButtonClicked
            commentId, userId, position -> onLikeButtonClicked(commentId, userId, position)
        },
        {
            // onItemClicked
            quotes, itemUsername -> onItemClicked(quotes, itemUsername)
        },
        {
            // onAvatarClicked
            username -> onAvatarClicked(username)
        },
        {
            // onPostUpdated
            newContent, commentId -> onPostUpdated(newContent, commentId)
        },
        sessionManager.getUserDetails().get(sessionManager.KEY_USERID) ?: Constants.NO_USER_STRING
    )

    @RequiresApi(Build.VERSION_CODES.N)
    fun onLikeButtonClicked(commentId: String, userId: String, position: Int) {
        if (userId != Constants.NO_USER_STRING) {
            val updatedLikeCount = viewModel.updateLike(
                commentId,
                Like(
                    sessionManager.getUserDetails().get(sessionManager.KEY_USERID)!!,
                    sessionManager.getUserDetails().get(sessionManager.KEY_USERNAME)!!
                )
            )

            adapter.notifyItemChanged(position, "like")

        } else {
            Toast.makeText(context, "Login for at sende dit like", Toast.LENGTH_SHORT).show()
        }
    }

    fun onPostUpdated(newContent: String, commentId: String) {
        viewModel.updateComment(newContent, commentId)
    }

    fun onItemClicked(quotes: Array<Quote>, itemUsername: String) {
        val action = PostFragmentDirections.actionPostFragmentToCreateCommentFragment(postId, quotes, itemUsername)
        findNavController().navigate(action)
    }

    fun onAvatarClicked(username: String) {
        val action = PostFragmentDirections.actionPostFragmentToProfileFragment(username, false)
        findNavController().navigate(action)
    }

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
                    PostFragmentDirections.actionPostFragmentToCreateCommentFragment(postId, null, "")
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