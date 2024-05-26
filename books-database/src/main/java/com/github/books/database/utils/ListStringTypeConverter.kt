package com.github.books.database.utils

import androidx.room.TypeConverter

class ListStringTypeConverter {
    @TypeConverter
    fun fromListIntToString(stringList: List<String>): String = stringList.toString()
    @TypeConverter
    fun toListStringFromString(stringList: String): List<String> {
        val result = ArrayList<String>()
        val split = stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toString())
            } catch (_: Exception) {

            }
        }
        return result
    }
}
