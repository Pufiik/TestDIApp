package ru.pugovishnikova.example.testdiapp.utils

data class LateInitData (
    private var id: Int,
    private var limit: Int
) {
    fun setId(id: Int) {
        this.id = id
    }

    fun setLimit(limit: Int) {
        this.limit = limit
    }

    fun getId() = id
    fun getLimit() = limit
}