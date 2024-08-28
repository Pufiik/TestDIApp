package ru.pugovishnikova.example.testdiapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.pugovishnikova.example.testdiapp.data.User
import ru.pugovishnikova.example.testdiapp.data.UserTypeConverter


@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(UserTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null
        fun getAppDBInstance(context: Context): AppDatabase {

            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    name = "APP_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return DB_INSTANCE!!
        }
    }
}