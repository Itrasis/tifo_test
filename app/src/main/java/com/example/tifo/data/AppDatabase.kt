package com.example.tifo.data

import android.content.Context
import android.os.Parcelable
import android.provider.ContactsContract.Data
import androidx.room.*
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


object Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToUser(value: String): List<DataModel.User> {
        return Gson().fromJson(value, Array<DataModel.User>::class.java).toList()
    }

    @TypeConverter
    fun userToString(list: List<DataModel.User>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}


@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository")
    fun getAll() : List<Repository>

    @Query("SELECT * FROM repository WHERE full_name LIKE '%' || :name || '%'")
    fun findByName(name: String): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repository: List<Repository>)
}


@Database(entities = [Repository::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao() : RepositoryDao

}

fun getDatabase(context : Context) : AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
}