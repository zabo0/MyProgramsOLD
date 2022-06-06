package com.saboon.myprograms.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.Models.ModelSubjectTime

@Dao
interface SubjectTimeDAO {

    @Insert
    suspend fun insert(subjectTime: ModelSubjectTime)

    @Query("SELECT * FROM ModelSubjectTime WHERE belowProgramID = :belowProgramID ORDER BY day ASC, timeStart ASC")
    suspend fun getAllSubjectTimes(belowProgramID:String): List<ModelSubjectTime>

    @Query("SELECT * FROM ModelSubjectTime WHERE id = :subjectTimeID")
    suspend fun getSubjectTime(subjectTimeID:String): ModelSubjectTime

    @Query("SELECT * FROM ModelSubjectTime WHERE belowSubjectID = :belowSubjectID")
    suspend fun getSubjectSubjectsTime(belowSubjectID:String): List<ModelSubjectTime>

    @Query("DELETE FROM ModelSubjectTime WHERE belowSubjectID = :belowSubjectID")
    suspend fun deleteAllSubjectTimes(belowSubjectID: String)

    @Query("DELETE FROM ModelSubjectTime WHERE id = :subjectTimeID")
    suspend fun deleteSubjectTime(subjectTimeID: String)

    @Query("UPDATE ModelSubjectTime SET " +
            "day = :day, " +
            "timeStart= :timeStart, " +
            "timeFinish = :timeFinish, " +
            "typeLesson = :typeTime, " +
            "classRoom = :classRoom, " +
            "reminderTime = :reminderTime, " +
            "belowSubjectID =:belowSubjectID, " +
            "belowProgramID = :belowProgramID  WHERE id = :id")
    suspend fun updateSubjectTime(id: String,
                                  day: String,
                                  timeStart: String,
                                  timeFinish: String,
                                  typeTime: String,
                                  classRoom: String,
                                  reminderTime: String,
                                  belowSubjectID: String,
                                  belowProgramID: String)
}