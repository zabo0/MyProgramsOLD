package com.saboon.myprograms.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.Models.Exam.ModelExam

@Dao
interface ExamDAO {

    @Insert
    suspend fun insert(exam: ModelExam)

    @Query("SELECT * FROM ModelExam WHERE belowProgramID = :belowProgramID")
    suspend fun getAllExams(belowProgramID: String): List<ModelExam>

    @Query("SELECT * FROM ModelExam WHERE id = :examID")
    suspend fun getExam(examID: String): ModelExam

    @Query("DELETE FROM ModelExam WHERE id = :examID")
    suspend fun deleteExam(examID: String)

    @Query("UPDATE ModelExam SET examName = :newExamName ,color =:newColor, day = :newDay, timeStart =:newTimeStart, timeFinish = :newTimeFinish, typeOfExam =:newTypeOfExam, classroom =:newClassroom, targetPoint=:newTargetPoint,reminderTime =:newReminderTime WHERE id=:examID ")
    suspend fun updateExam(
        examID: String,
        newExamName: String,
        newColor: String,
        newDay: String,
        newTimeStart: String,
        newTimeFinish: String,
        newTypeOfExam: String,
        newClassroom: String,
        newTargetPoint: String,
        newReminderTime: String,
    )

    @Query("UPDATE ModelExam SET isDone =:newIsDone WHERE id =:examID")
    suspend fun updateIsDone(examID: String, newIsDone: Boolean)

}