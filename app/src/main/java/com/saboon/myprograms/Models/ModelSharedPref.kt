package com.saboon.myprograms.Models

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity
data class ModelSharedPref(
    @ColumnInfo(name = "lastID")
    val lastID: String
)