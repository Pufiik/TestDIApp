package ru.pugovishnikova.example.testdiapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val maidenName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    @SerializedName("image")
    val imageUrl: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val eyeColor: String,
    val hair: Hair,
    val ip: String,
    val address: Address,
    val macAddress: String,
    val university: String,
    val bank: Bank,
    val company: Company,
    val ein: String,
    val ssn: String,
    val userAgent: String,
    val crypto: Crypto,
    val role: String
)

data class Hair(
    val color: String,
    val type: String
)

data class Address(
    val address: String,
    val city: String,
    val state: String,
    val stateCode: String,
    val postalCode: String,
    val coordinates: Coordinates,
    val country: String
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)

data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)

data class Company(
    val department: String,
    val name: String,
    val title: String,
    val address: CompanyAddress
)

data class CompanyAddress(
    val address: String,
    val city: String,
    val state: String,
    val stateCode: String,
    val postalCode: String,
    val coordinates: Coordinates,
    val country: String
)

data class Crypto(
    val coin: String,
    val wallet: String,
    val network: String
)


class UserTypeConverter {

    private val gson = Gson()


    @TypeConverter
    fun fromHair(value: Hair): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toHair(value: String?): Hair? {
        val listType = object : TypeToken<Hair>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromCompany(value: Company): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCompany(value: String?): Company? {
        val listType = object : TypeToken<Company>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromAddress(value: Address): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAddress(value: String?): Address? {
        val listType = object : TypeToken<Address>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromBank(value: Bank): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBank(value: String?): Bank? {
        val listType = object : TypeToken<Bank>() {}.type
        return gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromCrypto(value: Crypto): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCrypto(value: String?): Crypto? {
        val listType = object : TypeToken<Crypto>() {}.type
        return gson.fromJson(value, listType)
    }
}


