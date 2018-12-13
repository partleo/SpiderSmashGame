package com.example.leopa.testapplication.database


/*
import android.arch.persistence.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {

        }.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}*/
/*
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromUserList(list: List<ARGame>): LiveData<List<ARGame>> {
        val liveDataList = object : LiveData<List<ARGame>>() {
        }
        return liveDataList
    }

    @TypeConverter
    fun fromLiveDataList(list: LiveData<List<ARGame>>): List<ARGame> {
        val scoreList = list.value!!
        return scoreList
    }

}
*/