package com.saboon.myprograms.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ModelSharedPref(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "lastID")
    val lastID: String
)