package ru.pugovishnikova.example.testdiapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.datasource.UserDataSource
import ru.pugovishnikova.example.testdiapp.exceptions.UserDataException
import ru.pugovishnikova.example.testdiapp.repositories.UserRepository
import ru.pugovishnikova.example.testdiapp.utils.LateInitData
import ru.pugovishnikova.example.testdiapp.utils.State
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userDataSource: UserDataSource
) : ViewModel() {

    lateinit var lateInitData: LateInitData

    private val stateUsers = MutableStateFlow<State<List<User>>>(State.Idle())
    fun requireStateUsers() = stateUsers.asStateFlow()


    fun getInfoAboutUser() {
        viewModelScope.launch {
            stateUsers.value = State.Loading()
            userDataSource.getUserInfo(lateInitData.getId())
                .flowOn(Dispatchers.IO)
                .catch { e -> stateUsers.value = State.Fail(e) }
                .collect { user ->
                    stateUsers.value = State.Success(listOf(user))
                }
        }
    }

    fun getAllUsersFromServer() {
        viewModelScope.launch {
            stateUsers.value = State.Loading()
            try {
                val result = repository.getUsers(lateInitData.getLimit())
                stateUsers.value = State.Success(result.users)
            } catch (e: UserDataException) {
                stateUsers.value = State.Fail(e)
                getUsersFromDB()
            } catch (e: Exception) {
                getUsersFromDB()
            }
        }
    }


    private suspend fun getUsersFromDB() {
        userDataSource.getAllUsers()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                stateUsers.value = State.Fail(e)
            }
            .collect { users ->
                stateUsers.value = State.Success(users)
            }
    }
}