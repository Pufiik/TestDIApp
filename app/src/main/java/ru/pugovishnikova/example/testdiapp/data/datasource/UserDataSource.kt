package ru.pugovishnikova.example.testdiapp.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.pugovishnikova.example.testdiapp.data.User

interface UserDataSource {
    fun getAllUsers(): Flow<List<User>>
    fun getUserInfo(id: Int): Flow<User>
}