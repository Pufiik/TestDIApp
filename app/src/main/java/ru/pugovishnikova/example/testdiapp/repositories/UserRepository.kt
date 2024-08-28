package ru.pugovishnikova.example.testdiapp.repositories

import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.UserResponse

interface UserRepository {
    suspend fun getUsers(limit: Int): UserResponse
    suspend fun getUserByID(newsId: Int): User
}

