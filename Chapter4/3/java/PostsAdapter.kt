package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Post(
    val title: String, val content: String, val imageResId:
    Int, val time: String
)

class PostsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount() = posts.size
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.post_title)
        private val content: TextView = itemView.findViewById(R.id.post_content)
        private val image: ImageView = itemView.findViewById(R.id.post_image)
        private val time: TextView = itemView.findViewById(R.id.post_time)
        fun bind(post: Post) {
            title.text = post.title
            content.text = post.content
            image.setImageResource(post.imageResId)
            time.text = post.time
        }
    }
}
