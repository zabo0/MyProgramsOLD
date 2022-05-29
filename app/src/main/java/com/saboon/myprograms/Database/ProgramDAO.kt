package com.saboon.myprograms.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.Models.ModelProgram


@Dao
interface ProgramDAO {

    @Insert
    suspend fun insertProgram(program: ModelProgram)

    @Query("SELECT * FROM ModelProgram")
    suspend fun getAllPrograms(): List<ModelProgram>

    @Query("SELECT * FROM ModelProgram WHERE name LIKE :filter")
    suspend fun getAllProgramByFilter(filter: String): List<ModelProgram>

    @Query("SELECT * FROM ModelProgram WHERE id = :programID")
    suspend fun getProgram(programID:String):ModelProgram

    @Query("UPDATE ModelProgram SET name = :newName , dateEdited = :newDateEdited WHERE id = :programID")
    suspend fun updateProgram(programID: String, newName: String, newDateEdited: String)

    @Query("DELETE FROM ModelProgram WHERE id = :programID")
    suspend fun deleteProgram(programID: String)
}