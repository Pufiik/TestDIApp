package ru.pugovishnikova.example.testdiapp.data.dto
import com.google.gson.annotations.SerializedName
import ru.pugovishnikova.example.testdiapp.data.model.Post

data class PostResponse (
    @SerializedName("posts")
    val posts: List<Post>
)
