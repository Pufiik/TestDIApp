package ru.pugovishnikova.example.testdiapp.data.dto

import com.google.gson.annotations.SerializedName
import ru.pugovishnikova.example.testdiapp.data.model.User

data class UserResponse (
    @SerializedName("users")
    val users: List<User>
)
