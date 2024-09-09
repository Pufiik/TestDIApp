package ru.pugovishnikova.example.testdiapp.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pugovishnikova.example.testdiapp.data.db.AppDatabase
import ru.pugovishnikova.example.testdiapp.data.dao.PostDao
import ru.pugovishnikova.example.testdiapp.data.dao.UserDao
import ru.pugovishnikova.example.testdiapp.servicies.PostService
import ru.pugovishnikova.example.testdiapp.servicies.UserService


@Module
@InstallIn(SingletonComponent::class)
class NetworkingUserModule {

    @Provides
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    fun getUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }
    @Provides
    fun getPostDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.getPostDao()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun providePostService(retrofit: Retrofit): PostService {
        return retrofit.create(PostService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://dummyjson.com"
    }
}