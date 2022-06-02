package com.saboon.myprograms.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSubject(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "dateAdded")
    val dateAdded: String,

    @ColumnInfo(name = "dateEdited")
    var dateEdited: String,

    @ColumnInfo(name = "subjectName")
    var subjectName: String?,

    @ColumnInfo(name = "lecturerName")
    var lecturerName: String?,

    @ColumnInfo(name = "color")
    val color: String,

    @ColumnInfo(name = "absenteeism")
    var absenteeism: String,

    @ColumnInfo(name = "belowProgram")
    val belowProgram: String
)
