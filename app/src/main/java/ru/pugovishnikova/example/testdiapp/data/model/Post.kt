package ru.pugovishnikova.example.testdiapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "post")
data class Post(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val tags: List<String>,
    val reactions: Reactions,
    val views: Int,
    val userId: Int
)

data class Reactions(
    val likes: Int,
    val dislikes: Int
)


class PostTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromReactions(value: Reactions): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toReactions(value: String?): Reactions? {
        val listType = object : TypeToken<Reactions>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListString(value: List<String>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}
