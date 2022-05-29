package com.saboon.myprograms.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelProgram(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "dateAdded")
    val dateCreated: String,

    @ColumnInfo(name = "dateEdited")
    val dateEdited: String,
)