package ru.pugovishnikova.example.testdiapp.repositories.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import ru.pugovishnikova.example.testdiapp.data.dao.UserDao
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.servicies.UserService
import ru.pugovishnikova.example.testdiapp.exceptions.DataException
import ru.pugovishnikova.example.testdiapp.repositories.UserRepository
import javax.inject.Inject


@BoundTo(supertype = UserRepository::class, component = SingletonComponent::class)
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getAllUsersFromServer(): List<User> {
        val response = userService.getAllUsers()
        return when (response.isSuccessful) {
            true -> response.body()!!.users
            else -> throw DataException(GET_ALL_USERS_ERROR, USER_EXCEPTION)
        }
    }

    override suspend fun getAllLocalUsers(): List<User> = userDao.getAllUsers()
    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()


    override suspend fun getUserByIDFromServer(userId: Int): User {
        val response = userService.getUserById(userId)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw DataException(GET_USER_BY_ID, USER_EXCEPTION)
        }
    }

    override suspend fun getLocalUserByID(userId: Int): User = userDao.getUserById(userId)

    override suspend fun cacheUserToDB(userId: Int) {
        val response = userService.getUserById(userId)

        if (response.isSuccessful) {
            try {
                userDao.insertUser(response.body()!!)
            } catch (e: Exception) {
                throw DataException(CACHE_USER, USER_EXCEPTION)
            }
        } else {
            throw DataException(GET_USER_BY_ID, USER_EXCEPTION)
        }
    }

    override suspend fun updateUserFromDB(userId: Int) {
        val response = userService.getUserById(userId)

        if (response.isSuccessful) {
            try {
                userDao.updateUser(response.body()!!)
            } catch (e: Exception) {
                throw DataException(UPDATE_USER, USER_EXCEPTION)
            }
        } else {
            throw DataException(GET_USER_BY_ID, USER_EXCEPTION)
        }
    }

    override suspend fun deleteUserFromDB(userId: Int) {
        val response = userService.getUserById(userId)

        if (response.isSuccessful) {
            try {
                userDao.deleteUser(response.body()!!)
            } catch (e: Exception) {
                throw DataException(DELETE_USER, USER_EXCEPTION)
            }
        } else {
            throw DataException(GET_USER_BY_ID, USER_EXCEPTION)
        }
    }

    companion object {
        private const val GET_ALL_USERS_ERROR = "Ошибка получения всех пользователей"
        private const val GET_USER_BY_ID = "Ошибка получения пользователя по ID"
        private const val CACHE_USER = "Ошибка сохранения пользователя в БД"
        private const val UPDATE_USER = "Ошибка обновления пользователя в БД"
        private const val DELETE_USER = "Ошибка удаления пользователя из БД"
        private const val USER_EXCEPTION = "USER_EXCEPTION"
    }
}

