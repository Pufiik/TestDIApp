package ru.pugovishnikova.example.testdiapp.data.servicies

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.UserResponse

interface UserService {

    @GET("users")
    suspend fun getAllUsers(@Query("limit") limit: Int): Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

}
