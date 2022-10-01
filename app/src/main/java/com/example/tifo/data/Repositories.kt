package com.example.tifo.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Repository(
    @PrimaryKey val id : Int,
    val full_name: String? = null,
    val description: String? = null,
    val language: String? = null,
    val stargazers_count: String? = null,
    val branches: List<String>? = null,
    val contributors: List<DataModel.User>? = null,
) : Parcelable
