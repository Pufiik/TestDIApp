package ru.pugovishnikova.example.testdiapp.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

object CredentialStore {
    private const val TOKEN = "ID_CURRENT_USER"

    fun getToken(@ApplicationContext context: Context): String? = requirePreferences(context).getString(TOKEN, "")

    fun store(@ApplicationContext context: Context, uid: String) {
        with(requirePreferences(context).edit()) {
            putString(TOKEN, uid)
            apply()
        }
    }


    private fun requirePreferences(context: Context) =
        context.getSharedPreferences("file", Context.MODE_PRIVATE)
}
