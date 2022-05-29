package com.saboon.myprograms.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSubjectTime(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "day")
    val day: String?,

    @ColumnInfo(name ="timeStart")
    val timeStart: String?,

    @ColumnInfo(name ="timeFinish")
    val timeFinish: String?,

    @ColumnInfo(name = "typeLesson")
    val typeOfLesson: String?,

    @ColumnInfo(name ="classRoom")
    val classRoom: String?,

    @ColumnInfo(name ="reminderTime")
    val reminderTime: String?,

    @ColumnInfo(name = "notificationID")
    val notificationID: Int?,

    @ColumnInfo(name = "belowSubjectID")
    val belowSubjectID: String,

    @ColumnInfo(name = "belowProgramID")
    val belowProgramID: String
)