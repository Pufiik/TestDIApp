package ru.pugovishnikova.example.testdiapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.pugovishnikova.example.testdiapp.data.dao.PostDao
import ru.pugovishnikova.example.testdiapp.data.dao.UserDao
import ru.pugovishnikova.example.testdiapp.data.model.Post
import ru.pugovishnikova.example.testdiapp.data.model.PostTypeConverter
import ru.pugovishnikova.example.testdiapp.data.model.User
import ru.pugovishnikova.example.testdiapp.data.model.UserTypeConverter


@Database(entities = [User::class, Post::class], version = 2, exportSchema = false)
@TypeConverters(UserTypeConverter::class, PostTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getPostDao(): PostDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null
        fun getAppDBInstance(context: Context): AppDatabase {

            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    name = "APP_DB"
                )   .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return DB_INSTANCE!!
        }
    }
}