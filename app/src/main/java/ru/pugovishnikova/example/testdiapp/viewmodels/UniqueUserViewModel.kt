package ru.pugovishnikova.example.testdiapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
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
) : ViewModel() {
    lateinit var idClass: IdClass

    private var fetchJob: Job? = null
    private val stateUserInfo = MutableStateFlow<State<UserData>>(State.Idle())
    fun requireStateUserInfo() = stateUserInfo.asStateFlow()


    fun getInfoAboutUser() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            stateUserInfo.value = State.Loading()
            try {
                getUserData(idClass.getID())
            } catch (e: DataException) {
                if ((e.type == USER_EXCEPTION) || (e.type == POST_EXCEPTION)) {
                    val user = userRepository.getLocalUserByID(idClass.getID())
                    val posts = async {
                        postRepository.getUserPostsFromServer(idClass.getID())
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
        withTimeout(100000L) {
            val userInfo = async { userRepository.getUserByIDFromServer(id) }
            val userPosts = async { postRepository.getAllPostsFromServer() }

            val userInfoAwaited = userInfo.await()
            val userPostsAwaited = userPosts.await().filter { post -> post.userId == id }


            userRepository.cacheUserToDB(userInfoAwaited)
            stateUserInfo.value =
                State.Success(
                    UserData(
                        userInfoAwaited,
                        userPostsAwaited
                    )
                )
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