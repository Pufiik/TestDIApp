package ru.pugovishnikova.example.testdiapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.pugovishnikova.example.testdiapp.data.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post ORDER BY userId DESC LIMIT :limit OFFSET :offset")
    suspend fun getAllPosts(limit: Int, offset: Int): List<Post>

    @Query("SELECT * FROM post")
    fun observeUsersPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id= :id")
    suspend fun getPostById(id: Int): Post

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts(post: Post)

}