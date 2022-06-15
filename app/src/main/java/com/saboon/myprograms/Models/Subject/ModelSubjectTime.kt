package com.saboon.myprograms.Models.Subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelSubjectTime(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "day")
    var day: String?,

    @ColumnInfo(name ="timeStart")
    var timeStart: String?,

    @ColumnInfo(name ="timeFinish")
    var timeFinish: String?,

    @ColumnInfo(name = "typeLesson")
    var typeOfLesson: String?,

    @ColumnInfo(name ="classRoom")
    var classRoom: String?,

    @ColumnInfo(name ="reminderTime")
    var reminderTime: String?,

    @ColumnInfo(name = "notificationID")
    val notificationID: Int?,

    @ColumnInfo(name = "belowSubjectID")
    val belowSubjectID: String,

    @ColumnInfo(name = "belowProgramID")
    val belowProgramID: String
)
