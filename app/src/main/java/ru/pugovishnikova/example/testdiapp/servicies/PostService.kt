package ru.pugovishnikova.example.testdiapp.servicies

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pugovishnikova.example.testdiapp.data.dto.PostResponse
import ru.pugovishnikova.example.testdiapp.data.model.Post

interface PostService {
    @GET("posts")
    suspend fun getAllPosts(): Response<PostResponse>

    @GET("posts/{userId}")
    suspend fun getUserPosts(@Query("userId") userId: Int): Response<PostResponse>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

}