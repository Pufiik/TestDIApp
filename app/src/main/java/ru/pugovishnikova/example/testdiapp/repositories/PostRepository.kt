package ru.pugovishnikova.example.testdiapp.repositories

import ru.pugovishnikova.example.testdiapp.data.model.Post

interface PostRepository {
    suspend fun getAllPostsFromServer(): List<Post>
    suspend fun getUserPostsFromServer(userId: Int): List<Post>

    suspend fun getAllPostsFromDB(): List<Post>
    suspend fun getUserPostsFromDB(userId: Int): List<Post>

    suspend fun deletePostFromDB(post: Post)
    suspend fun deleteAllPostsFromDB()
    suspend fun updatePostFromDB(post: Post)

    suspend fun cachePostToDB(post: Post)

    suspend fun getPostByIDFromDB(postId: Int): Post
    suspend fun getPostByIDFromServer(postId: Int): Post
}