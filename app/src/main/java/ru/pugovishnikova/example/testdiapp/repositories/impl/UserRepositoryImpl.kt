package ru.pugovishnikova.example.testdiapp.repositories.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.UserResponse
import ru.pugovishnikova.example.testdiapp.data.servicies.UserService
import ru.pugovishnikova.example.testdiapp.exceptions.UserDataException
import ru.pugovishnikova.example.testdiapp.repositories.UserRepository
import javax.inject.Inject

@BoundTo(supertype = UserRepository::class, component = SingletonComponent::class)
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : UserRepository {
    override suspend fun getUsers(limit: Int): UserResponse {
        val response = userService.getAllUsers(limit)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw UserDataException(GET_ALL_USERS_ERROR)
        }
    }

    override suspend fun getUserByID(newsId: Int): User {
        val response = userService.getUserById(newsId)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw UserDataException(GET_USER_BY_ID)
        }
    }

    companion object {
        private const val GET_ALL_USERS_ERROR = "Ошибка получения всех пользователей"
        private const val GET_USER_BY_ID = "Ошибка получения пользователя по ID"
    }
}

