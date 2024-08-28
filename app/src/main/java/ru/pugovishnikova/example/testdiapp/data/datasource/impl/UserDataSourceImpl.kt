package ru.pugovishnikova.example.testdiapp.data.datasource.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.datasource.UserDataSource
import ru.pugovishnikova.example.testdiapp.data.db.UserDao
import javax.inject.Inject

@BoundTo(supertype = UserDataSource::class, component = SingletonComponent::class)
class UserDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserDataSource {
    override fun getAllUsers(): Flow<List<User>> = flow {
        emit(userDao.getAllUsers())
    }

    override fun getUserInfo(id: Int): Flow<User> = flow {
        emit(userDao.getUserById(id))
    }

}