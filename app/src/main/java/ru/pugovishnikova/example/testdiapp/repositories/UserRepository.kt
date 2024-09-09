package ru.pugovishnikova.example.testdiapp.repositories
import ru.pugovishnikova.example.testdiapp.data.model.User
interface UserRepository {
    suspend fun getAllUsersFromServer(): List<User>
    suspend fun getAllLocalUsers(): List<User>

    suspend fun deleteAllUsers()
    suspend fun getUserByIDFromServer(userId: Int): User
    suspend fun getLocalUserByID(userId: Int): User
    suspend fun cacheUserToDB(userId: Int)

    suspend fun updateUserFromDB(userId: Int)

    suspend fun deleteUserFromDB(userId: Int)
}

