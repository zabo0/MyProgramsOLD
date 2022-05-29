package com.saboon.myprograms.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.Models.ModelSubject


@Dao
interface SubjectDAO {

    @Insert
    suspend fun insert(subject: ModelSubject)

    @Query("SELECT * FROM ModelSubject")
    suspend fun getAllSubjects()

    @Query("SELECT * FROM ModelSubject WHERE subjectName = :subjectName")
    suspend fun getAllSubjectByFilter(subjectName: String)

    @Query("SELECT * FROM ModelSubject WHERE id = :subjectID")
    suspend fun getSubject(subjectID: String)

    @Query("DELETE FROM ModelSubject WHERE id = :subjectID")
    suspend fun deleteSubject(subjectID: String)

    @Query("UPDATE ModelSubject SET subjectName= :newSubjectName, lecturerName = :newLecturerName, color = :newColor, dateEdited = :newDateEdited WHERE id = :subjectID ")
    suspend fun updateSubject(subjectID:String, newSubjectName:String, newLecturerName:String, newColor:String,newDateEdited:String)

    @Query("UPDATE ModelSubject SET absenteeism = :newAbsenteeism WHERE id = :subjectID")
    suspend fun updateAbsenteeism(subjectID: String, newAbsenteeism:String)
}