package ru.pugovishnikova.example.testdiapp.repositories
import ru.pugovishnikova.example.testdiapp.data.model.User
interface UserRepository {
    suspend fun getAllUsersFromServer(): List<User>
    suspend fun getAllLocalUsers(): List<User>

    suspend fun deleteAllUsers()
    suspend fun getUserByIDFromServer(userId: Int): User
    suspend fun getLocalUserByID(userId: Int): User
    suspend fun cacheUserToDB(user: User)

    suspend fun updateUserFromDB(user: User)

    suspend fun deleteUserFromDB(user: User)
}

