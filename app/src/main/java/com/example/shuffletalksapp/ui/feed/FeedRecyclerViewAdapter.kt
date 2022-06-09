package com.example.shuffletalksapp.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shuffletalksapp.databinding.ItemFeedBinding

class FeedRecyclerViewAdapter(): RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>() {

    private var posts = ArrayList<FeedItemUIModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemFeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        //return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: FeedRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setData(data: List<FeedItemUIModel>) {
        posts.clear()
        if (data.isNullOrEmpty()) {
            return
        } else {
            posts.addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val postBinding: ItemFeedBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
        fun bind(model: FeedItemUIModel) {


            postBinding.apply {
                usernameTextView.text = model.username
                postCountTextView.text = model.postcount.toString()
                createdAtTextView.text = model.createdAt
                contentTextView.text = model.content
                Glide.with(this.root)
                    .load(model.avatar)
                    .into(postBinding.avatarRoundedImageView)

                root.setOnClickListener {
                    val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(model.postId)
                    Navigation.findNavController(root).navigate(action)

                }


            }
        }


    }
}