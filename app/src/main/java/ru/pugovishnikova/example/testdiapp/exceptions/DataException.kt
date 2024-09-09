package ru.pugovishnikova.example.testdiapp.exceptions

class DataException(message: String, val type: String = "Unknown") : Exception(message) {
}