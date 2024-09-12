package ru.pugovishnikova.example.testdiapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.pugovishnikova.example.testdiapp.data.model.Post
import ru.pugovishnikova.example.testdiapp.databinding.UserPostItemBinding

class PostAdapter(
    private val posts: List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val binding = UserPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class PostViewHolder(private val binding: UserPostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.postId.text = post.id.toString()
            binding.postTitle.text = post.title
            binding.likesDislikes.text = "likes: ${post.reactions.likes}, dislikes: ${post.reactions.dislikes}"
        }
    }
}