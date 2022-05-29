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
    val dateEdited: String,

    @ColumnInfo(name = "subjectName")
    val subjectName: String?,

    @ColumnInfo(name = "lecturerName")
    val lecturerName: String?,

    @ColumnInfo(name = "color")
    val color: String,

    @ColumnInfo(name = "absenteeism")
    val absenteeism: String,

    @ColumnInfo(name = "belowProgram")
    val belowProgram: String
)
