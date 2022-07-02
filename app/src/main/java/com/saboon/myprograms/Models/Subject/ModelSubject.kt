package com.saboon.myprograms.Models.Subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSubject(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "dateAdded")
    val dateAdded: Long,

    @ColumnInfo(name = "dateEdited")
    var dateEdited: Long,

    @ColumnInfo(name = "subjectName")
    var subjectName: String?,

    @ColumnInfo(name = "lecturerName")
    var lecturerName: String?,

    @ColumnInfo(name = "color")
    var color: String,

    @ColumnInfo(name = "absenteeism")
    var absenteeism: String,

    @ColumnInfo(name = "belowProgram")
    val belowProgram: String
)
