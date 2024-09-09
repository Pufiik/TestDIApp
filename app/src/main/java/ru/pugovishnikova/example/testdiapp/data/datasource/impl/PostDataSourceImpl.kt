package ru.pugovishnikova.example.testdiapp.data.datasource.impl

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.pugovishnikova.example.testdiapp.data.datasource.PostDataSource
import ru.pugovishnikova.example.testdiapp.data.dao.PostDao
import ru.pugovishnikova.example.testdiapp.data.model.Post
import ru.pugovishnikova.example.testdiapp.utils.LimitClass
import javax.inject.Inject


@BoundTo(supertype = PostDataSource::class, component = SingletonComponent::class)
class PostDataSourceImpl @Inject constructor(
    private val postDao: PostDao,
) : PostDataSource {

    lateinit var limit: LimitClass
    override fun getAllPosts(): Flow<Post> = flow {
        var page = 0
        do {
            val posts = postDao.getAllPosts(limit.getLimit(), page * limit.getLimit()).also {
                page++
            }
            emitAll(posts.asFlow())
        } while (posts.isNotEmpty())
    }

    override suspend fun getPostInfo(id: Int): Post = coroutineScope {
        getAllPosts().first { it.id == id }
    }
}