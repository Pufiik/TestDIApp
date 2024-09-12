package ru.pugovishnikova.example.testdiapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.exceptions.DataException
import ru.pugovishnikova.example.testdiapp.repositories.UserRepository
import ru.pugovishnikova.example.testdiapp.utils.State
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var fetchJob: Job? = null

    private val stateUsers = MutableStateFlow<State<List<User>>>(State.Idle())
    fun requireStateUsers() = stateUsers.asStateFlow()

    fun getAllUsersFromServer() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withTimeout(100000L) {
                    stateUsers.value = State.Loading()
                    try {
                        val result = userRepository.getAllUsersFromServer()
                        stateUsers.value = State.Success(result)
                    } catch (e: DataException) {
                        stateUsers.value = State.Fail(e)
                        getUsersFromDB()
                    } catch (e: TimeoutCancellationException) {
                        stateUsers.value =
                            State.Fail(e.message?.let { DataException(it, USER_EXCEPTION) } ?: e)
                        getUsersFromDB()
                    } catch (e: Exception) {
                        stateUsers.value = State.Fail(e)
                        getUsersFromDB()
                    }
                }
            }
        }
    }

    private suspend fun getUsersFromDB() {
        try {
            val result = userRepository.getAllLocalUsers()
            stateUsers.value = State.Success(result)
        } catch (e: Exception) {
            stateUsers.value =
                State.Fail(e.message?.let { DataException(it, USER_EXCEPTION) } ?: e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }

    companion object {
        private const val USER_EXCEPTION = "USER_EXCEPTION"
    }
}