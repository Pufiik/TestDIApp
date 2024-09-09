package ru.pugovishnikova.example.testdiapp.servicies

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.data.dto.UserResponse

interface UserService {

    @GET("users")
    suspend fun getAllUsers(): Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

}
