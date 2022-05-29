package com.saboon.myprograms.Database

import androidx.room.Database
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime


@Database(entities = arrayOf(ModelProgram::class, ModelSubject::class, ModelSubjectTime::class, ModelStates::class), version = 1)
class Database {

}