package ru.pugovishnikova.example.testdiapp.repositories.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import ru.pugovishnikova.example.testdiapp.data.dao.PostDao
import ru.pugovishnikova.example.testdiapp.data.dto.PostResponse
import ru.pugovishnikova.example.testdiapp.data.model.Post
import ru.pugovishnikova.example.testdiapp.exceptions.DataException
import ru.pugovishnikova.example.testdiapp.repositories.PostRepository
import ru.pugovishnikova.example.testdiapp.servicies.PostService
import javax.inject.Inject


@BoundTo(supertype = PostRepository::class, component = SingletonComponent::class)
class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao
) : PostRepository {
    override suspend fun getPosts(): PostResponse {
        val response = postService.getAllPosts()
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw DataException(GET_ALL_POSTS_ERROR, POST_EXCEPTION)
        }
    }

    override suspend fun getPostByID(postId: Int): Post {
        val response = postService.getPostById(postId)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw DataException(GET_POST_BY_ID, POST_EXCEPTION)
        }
    }

    override suspend fun getUserPosts(userId: Int): PostResponse {
        val response = postService.getUserPosts(userId)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw DataException(GET_USER_POSTS_ERROR, POST_EXCEPTION)
        }
    }

    companion object {
        private const val GET_ALL_POSTS_ERROR = "Ошибка получения всех постов"
        private const val GET_USER_POSTS_ERROR = "Ошибка получения постов пользователя"
        private const val GET_POST_BY_ID = "Ошибка получения поста по ID"
        private const val POST_EXCEPTION = "POST_EXCEPTION"
    }
}