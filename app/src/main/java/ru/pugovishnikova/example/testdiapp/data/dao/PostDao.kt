package ru.pugovishnikova.example.testdiapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.pugovishnikova.example.testdiapp.data.model.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post ")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM post  where userid= :id ORDER BY userId")
    fun getUsersPosts(id : Int): List<Post>


    @Query("SELECT * FROM post WHERE id= :id")
    suspend fun getPostById(id: Int): Post

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()

}