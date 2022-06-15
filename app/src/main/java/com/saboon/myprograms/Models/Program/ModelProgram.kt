package com.saboon.myprograms.Models.Program

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelProgram(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "dateAdded")
    var dateCreated: String,

    @ColumnInfo(name = "dateEdited")
    var dateEdited: String,

    @ColumnInfo(name = "typeOfProgram")
    val typeOfProgram: String

)