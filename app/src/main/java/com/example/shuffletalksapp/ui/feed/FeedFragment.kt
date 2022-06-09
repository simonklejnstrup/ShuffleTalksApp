package com.example.shuffletalksapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.FragmentFeedBinding
import com.example.shuffletalksapp.repository.Repository
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants

class FeedFragment: Fragment(R.layout.fragment_feed) {
    private val viewModel: FeedViewModel by viewModels {
        FeedViewModelFactory(Repository())
    }

    private lateinit var binding: FragmentFeedBinding
    private val adapter = FeedRecyclerViewAdapter()
    private val sessionManager = SessionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized) {
            binding = FragmentFeedBinding.inflate(inflater)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContent()

        binding.fab.setOnClickListener {
            if (sessionManager.isLoggedIn()) {
                findNavController().navigate(R.id.createPostFragment)
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }

    }



    private fun initContent() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
        adapter.setData(viewModel.feedCardItems)

    }




}