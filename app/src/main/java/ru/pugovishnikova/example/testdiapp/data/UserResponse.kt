package ru.pugovishnikova.example.testdiapp.data

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("users")
    val users: List<User>
)
