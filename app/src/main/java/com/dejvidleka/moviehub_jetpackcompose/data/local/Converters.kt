package com.dejvidleka.moviehub_jetpackcompose.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()
    private val type = object : TypeToken<List<Int>>() {}.type

    @TypeConverter
    fun fromStringList(list: List<Int>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJson(value: String): List<Int> {
        return try {
            gson.fromJson(value, type)
        } catch (e: Exception) {
            emptyList()
        }
    }
}