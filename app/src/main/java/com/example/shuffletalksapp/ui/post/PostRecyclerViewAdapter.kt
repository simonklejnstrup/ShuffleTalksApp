package com.example.shuffletalksapp.ui.post

import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.ItemPostBinding
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.model.Quote
import com.example.shuffletalksapp.session.SessionManager

class PostRecyclerViewAdapter(
    private var onLikeButtonClicked: ((commentId: String, userId: String, position: Int) -> Unit),
    private var onItemClicked: ((quoteList: Array<Quote>, itemUsername: String) -> Unit),
    private var onAvatarClicked: ((username: String) -> Unit),
    private var onPostUpdated: ((newContent: String, commentId: String) -> Unit),
    private val userId: String
) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {

    private var comments = ArrayList<PostItemUIModel>()
    private val sessionManager = SessionManager()
    private var isLiked = false
    private val currentUserId = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERID)
    private val currentUsername = sessionManager.getUserDetails()?.get(sessionManager.KEY_USERNAME)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(holder: PostRecyclerViewAdapter.ViewHolder,
                                  position: Int) {
        holder.bind(comments[position], position)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun setData(data: List<PostItemUIModel>) {
        comments.clear()
        if (data.isNullOrEmpty()) {
            return
        } else {
            comments.addAll(data)
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val postBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
        fun bind(model: PostItemUIModel,
                 position: Int) {

            postBinding.apply {

                usernameTextView.text = model.username
                postCountTextView.text = model.postcount.toString()
                createdAtTextView.text = model.createdAt
                contentTextView.text = model.content

                Glide.with(this.root)
                    .load(model.avatar)
                    .into(postBinding.avatarRoundedImageView)

                // Setup edit button if session user equals author of post
                if (currentUserId == model.userId) {
                    postBinding.editBtn.visibility = View.VISIBLE
                    postBinding.editBtn.setOnClickListener {
                        postBinding.apply {
                            editText.visibility = View.VISIBLE
                            editText.setText(model.content)
                            editText.requestFocus()
                            editBtn.setImageResource(R.drawable.btn_check_green_rust_round)
                            contentTextView.visibility = View.GONE

                            // setup button to finish editing
                            editBtn.setOnClickListener {
                                onPostUpdated(editText.text.toString(), model.commentId)
                                postBinding.apply {
                                    contentTextView.text = editText.text.toString()
                                    contentTextView.visibility = View.VISIBLE
                                    editText.visibility = View.GONE
                                    editBtn.setImageResource(R.drawable.btn_edit_green_round)
                                }
                            }

                        }

                    }
                }

                // Handle quotes
                if (model.quotes.size > 0) {
                    postBinding.apply {
                        quoteContainer.visibility = View.VISIBLE
                        quoteContainer.removeAllViews()
                        for (quote in model.quotes) {
                            val layout = LinearLayout(itemView.context)
                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)
                            layout.layoutParams = params
                            layout.orientation = LinearLayout.HORIZONTAL
                            layout.setPadding(20,0,15,15)

                            val authorTextView = TextView(itemView.context)
                            authorTextView.text = quote.username
                            authorTextView.setTypeface(null, Typeface.BOLD)
                            authorTextView.setPadding(0, 0, 10, 0)

                            val contentTextView = TextView(itemView.context)
                            contentTextView.text = quote.text
                            contentTextView.maxLines = 5
                            contentTextView.ellipsize = TextUtils.TruncateAt.END

                            layout.addView(authorTextView)
                            layout.addView(contentTextView)

                            quoteContainer.addView(layout)
                        }

                    }
                } else {
                    quoteContainer.visibility = View.GONE
                }

                // Show author tag if user == OP
                if (model.userId == comments[0].userId) {
                    postBinding.opTextView.visibility = View.VISIBLE
                } else {
                    postBinding.opTextView.visibility = View.GONE
                }

                // Setup like button for currentuser
                isLiked = model.likes.any { like -> like.userId == currentUserId }
                if (isLiked) {
                    btnLikeImageView.setImageResource(R.drawable.btn_like_active)
                } else {
                    btnLikeImageView.setImageResource(R.drawable.btn_like_inactive)
                }

                if (model.likes.size == 0) {
                    likesCounterLayout.visibility = View.GONE
                }
                if (model.likes.size > 0) {
                    likesCounterLayout.visibility = View.VISIBLE
                    likesCounterTextView.visibility = View.VISIBLE
                    likesCounterTextView.text = model.likes.size.toString()
                }

                btnLike.setOnClickListener {

                    if (sessionManager.isLoggedIn()) {
                        if (isLiked) {
                            btnLikeImageView.setImageResource(R.drawable.btn_like_inactive)
                        } else {
                            btnLikeImageView.setImageResource(R.drawable.btn_like_active)
                        }

                    }

                    onLikeButtonClicked(
                        model.commentId,
                        userId,
                        position
                    )
                }


                // OnclickListener for the whole Card
                root.setOnClickListener {
                    if (sessionManager.isLoggedIn()) {
                        val quoteList = ArrayList<Quote>()
                        for (quote in model.quotes) {
                            quoteList.add(quote)
                        }
                        quoteList.add(Quote(
                            model.commentId,
                            model.username,
                            model.userId,
                            model.content
                        ))

                        val quoteArray = quoteList.toTypedArray()

                        onItemClicked(quoteArray, model.username)

                    }
                }

                // OnclickListener for avatar
                avatarRoundedImageView.setOnClickListener {
                    onAvatarClicked(model.username)
                }

            }


        }



    }
}
