package ru.pugovishnikova.example.testdiapp.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.pugovishnikova.example.testdiapp.data.model.Post

interface PostDataSource {
    fun getAllPosts(): Flow<Post>
    suspend fun getPostInfo(id: Int):Post
}