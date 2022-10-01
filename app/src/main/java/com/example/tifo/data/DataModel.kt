package com.example.tifo.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class DataModel : Parcelable {
     data class User(
        val login: String,
        val avatar_url : String
    ) : DataModel()

    data class Branch(
        val name : String
    ) : DataModel ()

    data class Header(
        val title : String
    ) : DataModel()
}
