package io.fhate.data.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.fhate.data.model.Image

class ImageConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Image?>? {
        if (data == null) {
            return listOf()
        }
        val listType = object : TypeToken<List<Image?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Image?>?): String? {
        return gson.toJson(someObjects)
    }

}