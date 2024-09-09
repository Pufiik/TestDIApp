package ru.pugovishnikova.example.testdiapp.repositories

import ru.pugovishnikova.example.testdiapp.data.dto.PostResponse
import ru.pugovishnikova.example.testdiapp.data.model.Post

interface PostRepository {
    suspend fun getPosts(): PostResponse
    suspend fun getUserPosts(userId: Int): PostResponse
    suspend fun getPostByID(postId: Int): Post

}