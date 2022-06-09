package com.example.shuffletalksapp.ui.post

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.ItemPostBinding
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.session.SessionManager
import com.example.shuffletalksapp.util.Constants

class PostRecyclerViewAdapter(
    private var onLikeButtonClicked: ((commentId: String, userId: String) -> Unit),
    private var onItemClicked: ((commentId: String) -> Unit),
    private var onAvatarClicked: ((username: String) -> Unit),
    private var onPostUpdated: ((newContent: String, commentId: String) -> Unit),
    private val userId: String
) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {

    private var comments = ArrayList<PostItemUIModel>()
    private val sessionManager = SessionManager()
    private var likeFlag = false
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


    override fun onBindViewHolder(holder: PostRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(comments[position])
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
        fun bind(model: PostItemUIModel) {

            postBinding.apply {
                usernameTextView.text = model.username
                postCountTextView.text = model.postcount.toString()
                createdAtTextView.text = model.createdAt
                contentTextView.text = model.content

                Glide.with(this.root)
                    .load(model.avatar)
                    .into(postBinding.avatarRoundedImageView)

                if (model.likes.size == 0) {
                    likesCounterTextView.visibility = View.INVISIBLE
                }
                if (model.likes.size > 0) {
                    likesCounterTextView.visibility = View.VISIBLE
                    likesCounterTextView.text = model.likes.size.toString()
                }

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

                if (model.quotes.size > 0) {
                    postBinding.apply {
                        quoteContainer.visibility = View.VISIBLE
                        for (quote in model.quotes) {
                            val layout = LinearLayout(itemView.context)
                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)
                            layout.layoutParams = params
                            layout.orientation = LinearLayout.HORIZONTAL

                            val authorTextView = TextView(itemView.context)
                            authorTextView.text = quote.username
                            authorTextView.setTypeface(null, Typeface.BOLD)
                            authorTextView.setPadding(4, 4, 4, 4) // virker ikke

                            val contentTextView = TextView(itemView.context)
                            contentTextView.text = quote.text
                            contentTextView.setPadding(4, 4, 4, 4) // virker ikke
                            contentTextView.maxLines = 5
                            contentTextView.ellipsize = TextUtils.TruncateAt.END

                            layout.addView(authorTextView)
                            layout.addView(contentTextView)

                            quoteContainer.addView(layout)
                        }

                    }
                }

                // Show author tag if user == OP
                if (model.userId == comments[0].userId) {
                    postBinding.opTextView.visibility = View.VISIBLE
                }


                // Set like button
                var isLiked = model.likes.any { like -> like.userId == userId }
                if (isLiked) {
                    btnLikeImageView.setImageResource(R.drawable.btn_like_active)
                } else {
                    btnLikeImageView.setImageResource(R.drawable.btn_like_inactive)
                }


                btnLike.setOnClickListener {

                    onLikeButtonClicked(
                        model.commentId,
                        userId
                    )

                    if (isLiked) {
                        btnLikeImageView.setImageResource(R.drawable.btn_like_active)
                    } else {
                        btnLikeImageView.setImageResource(R.drawable.btn_like_inactive)
                    }
                    isLiked = !isLiked
                }

                // TODO: show quotes if there are any
                // commentCountTextView.text = model.commentCount

                // OnclickListener for the whole Card
                root.setOnClickListener {
                    onItemClicked(model.commentId)
                }

                avatarRoundedImageView.setOnClickListener {
                    println(userId)

                    //onAvatarClicked(model.username)
                }

            }


        }


    }
}
