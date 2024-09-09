package ru.pugovishnikova.example.testdiapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ru.pugovishnikova.example.testdiapp.data.datasource.PostDataSource
import ru.pugovishnikova.example.testdiapp.data.dto.UserData
import ru.pugovishnikova.example.testdiapp.exceptions.DataException
import ru.pugovishnikova.example.testdiapp.repositories.PostRepository
import ru.pugovishnikova.example.testdiapp.repositories.UserRepository
import ru.pugovishnikova.example.testdiapp.utils.IdClass
import ru.pugovishnikova.example.testdiapp.utils.State
import javax.inject.Inject

@HiltViewModel
class UniqueUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val postDataSource: PostDataSource
) : ViewModel() {

    lateinit var id: IdClass
    val limit = 20
    private var fetchJob: Job? = null

    private val stateUserInfo = MutableStateFlow<State<UserData>>(State.Idle())
    fun requireStateUserInfo() = stateUserInfo.asStateFlow()


    fun getInfoAboutUser() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            stateUserInfo.value = State.Loading()
            try {
                getUserData(id.getID())
            } catch (e: DataException) {
                if ((e.type == USER_EXCEPTION) || (e.type == POST_EXCEPTION)) {
                    val user = userRepository.getLocalUserByID(id.getID())
                    val posts = async {
                        postDataSource.getAllPosts()
                            .filter {
                                it.userId == id.getID()
                            }
                    }
                    stateUserInfo.value =
                        State.Success(UserData(user, posts.await().toList()))
                } else State.Fail(e)

            } catch (e: Exception) {
                State.Fail(e)
            }
        }
    }


    private suspend fun getUserData(id: Int) = withContext(Dispatchers.IO) {
        withTimeout(5000L) {
            val userInfo = async { userRepository.getUserByIDFromServer(id) }
            val posts = async { postRepository.getPosts() }

            stateUserInfo.value =
                State.Success(UserData(userInfo.await(), posts.await().posts))
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }

    companion object {
        private const val USER_EXCEPTION = "USER_EXCEPTION"
        private const val POST_EXCEPTION = "POST_EXCEPTION"
    }
}