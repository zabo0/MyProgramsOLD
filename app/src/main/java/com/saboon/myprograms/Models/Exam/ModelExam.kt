package com.saboon.myprograms.Models.Exam

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelExam(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "dateCreated")
    val dateCreated: String,
    @ColumnInfo(name = "dateEdited")
    var dateEdited: String,
    @ColumnInfo(name = "examName")
    var examName: String?,
    @ColumnInfo(name = "color")
    var color: String,
    @ColumnInfo(name = "day")
    var day: String,
    @ColumnInfo(name = "timeStart")
    var timeStart: String?,
    @ColumnInfo(name = "timeFinish")
    var timeFinish: String?,
    @ColumnInfo(name = "typeOfExam")
    var typeOfExam: String?,
    @ColumnInfo(name = "classroom")
    var classroom: String?,
    @ColumnInfo(name = "targetPoint")
    var targetPoint: String,
    @ColumnInfo(name = "reminderTime")
    var reminderTime: String,
    @ColumnInfo(name = "isDone")
    var isDone: Boolean,
    @ColumnInfo(name = "notificationID")
    val notificationID: String?,
    @ColumnInfo(name = "belowProgramID")
    val belowProgramID: String

)