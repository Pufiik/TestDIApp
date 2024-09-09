package ru.pugovishnikova.example.testdiapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.databinding.UserItemBinding

class UserAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userId.text = user.id.toString()
            binding.userName.text = user.firstName
            binding.userSurname.text = user.lastName
            binding.userImage.apply {
                Glide.with(this)
                    .load(user.imageUrl)
                    .into(this)
            }
        }
    }
}