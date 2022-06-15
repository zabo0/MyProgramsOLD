package com.saboon.myprograms.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saboon.myprograms.Models.*
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime


@Database(entities = arrayOf(ModelProgram::class, ModelSubject::class, ModelSubjectTime::class, ModelSharedPref::class), version = 2)
abstract class DatabaseMyPrograms: RoomDatabase() {


    abstract fun programDAO() : ProgramDAO
    abstract fun subjectDAO() : SubjectDAO
    abstract fun subjectTimeDAO(): SubjectTimeDAO
    abstract fun sharedPrefDAO(): SharedPrefDAO


    //\\--SINGLETON--//\\

    companion object{
        @Volatile
        private var instance : DatabaseMyPrograms? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }



        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DatabaseMyPrograms::class.java, "MyProgramsDatabase"
        ).build()

        //.enableMultiInstanceInvalidation()
    }

}