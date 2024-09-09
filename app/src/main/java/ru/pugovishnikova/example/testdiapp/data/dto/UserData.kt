package ru.pugovishnikova.example.testdiapp.data.dto

import ru.pugovishnikova.example.testdiapp.data.model.Post
import ru.pugovishnikova.example.testdiapp.data.model.User

data class UserData(val user: User, val posts: List<Post>)