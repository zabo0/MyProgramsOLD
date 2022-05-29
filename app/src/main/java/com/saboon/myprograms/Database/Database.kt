package com.saboon.myprograms.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime


@Database(entities = arrayOf(ModelProgram::class, ModelSubject::class, ModelSubjectTime::class, ModelStates::class), version = 1)
abstract class Database: RoomDatabase() {


    abstract fun programDAO() : ProgramDAO


    //\\--SINGLETON--//\\

    companion object{
        @Volatile
        private var instance : com.saboon.myprograms.Database.Database? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }



        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            com.saboon.myprograms.Database.Database::class.java, "MyProgramsDatabase"
        ).build()

        //.enableMultiInstanceInvalidation()
    }

}