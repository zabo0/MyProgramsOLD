package com.saboon.myprograms.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saboon.myprograms.Models.ModelSharedPref

@Dao
interface SharedPrefDAO {
    @Insert
    suspend fun insert(sharedPref:ModelSharedPref)

    @Query("SELECT * FROM ModelSharedPref WHERE id = :sharedPrefID")
    suspend fun getSharedPref(sharedPrefID:String): ModelSharedPref

    @Query("DELETE FROM ModelSharedPref WHERE id = :sharedPrefID")
    suspend fun deleteLastProgramID(sharedPrefID: String)
}