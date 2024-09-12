package ru.pugovishnikova.example.testdiapp.repositories.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import ru.pugovishnikova.example.testdiapp.data.dao.PostDao
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
    override suspend fun getAllPostsFromServer(): List<Post> {
        val response = postService.getAllPosts()
        return when (response.isSuccessful) {
            true -> response.body()!!.posts
            else -> throw DataException(GET_ALL_POSTS_ERROR, POST_EXCEPTION)
        }
    }

    override suspend fun getUserPostsFromServer(userId: Int): List<Post> {
        val response = postService.getUserPosts(userId)
        return when (response.isSuccessful) {
            true -> response.body()!!.posts
            else -> throw DataException(GET_USER_POSTS_ERROR, POST_EXCEPTION)
        }
    }

    override suspend fun getAllPostsFromDB(): List<Post> = postDao.getAllPosts()
    override suspend fun getUserPostsFromDB(userId: Int): List<Post> = postDao.getUsersPosts(userId)


    override suspend fun deletePostFromDB(post: Post) {
        try {
            postDao.deletePost(post)
        } catch (e: Exception) {
            throw DataException(DELETE_POST, POST_EXCEPTION)
        }
    }

    override suspend fun deleteAllPostsFromDB() = postDao.deleteAllPosts()

    override suspend fun updatePostFromDB(post: Post) {
        try {
            postDao.updatePost(post)
        } catch (e: Exception) {
            throw DataException(UPDATE_POST, POST_EXCEPTION)
        }
    }

    override suspend fun cachePostToDB(post: Post) {
        try {
            postDao.insertPost(post)
        } catch (e: Exception) {
            throw DataException(CACHE_POST, POST_EXCEPTION)
        }
    }


    override suspend fun getPostByIDFromDB(postId: Int): Post = postDao.getPostById(postId)

    override suspend fun getPostByIDFromServer(postId: Int): Post {
        val response = postService.getPostById(postId)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw DataException(GET_POST_BY_ID, POST_EXCEPTION)
        }
    }

    companion object {
        private const val GET_ALL_POSTS_ERROR = "Ошибка получения всех постов"
        private const val GET_USER_POSTS_ERROR = "Ошибка получения постов пользователя"
        private const val GET_POST_BY_ID = "Ошибка получения поста по ID"
        private const val CACHE_POST = "Ошибка сохранения поста в БД"
        private const val UPDATE_POST = "Ошибка обновления поста в БД"
        private const val DELETE_POST = "Ошибка удаления поста из БД"
        private const val POST_EXCEPTION = "POST_EXCEPTION"
    }
}