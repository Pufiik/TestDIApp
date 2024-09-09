package ru.pugovishnikova.example.testdiapp.utils

data class IdClass(
    private var id: Int
) {
    fun getID() = id
    fun setID(newId: Int) {
        id = newId
    }
}