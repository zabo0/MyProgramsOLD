package com.saboon.myprograms.Utils

import java.util.*

class IDGenerator {

    fun generateProgramID(programName : String): String{
        val progName = programName.filter { !it.isWhitespace() }
        val randomID = UUID.randomUUID().toString()
        val id = progName + "_" + randomID
        return id
    }


    fun generateSubjectID(programName: String, subjectName: String): String{
        val progName = programName.filter { !it.isWhitespace() }
        val sbjName = subjectName.filter { !it.isWhitespace() }
        val randomID = UUID.randomUUID().toString()
        val id = "${sbjName}_${progName}_${randomID}"
        return id

    }

    fun generateTimeID(programName: String, subjectName: String, day_timeStart: String): String{
        val progName = programName.filter { !it.isWhitespace() }
        val sbjName = subjectName.filter { !it.isWhitespace() }
        val time = day_timeStart.filter { !it.isWhitespace() }
        val randomID = UUID.randomUUID().toString()
        val id = "${time}_${sbjName}_${progName}_${randomID}"
        return id
    }

    fun generateExamID(programName: String, examName: String): String{
        val progName = programName.filter { !it.isWhitespace() }
        val exmName = examName.filter { !it.isWhitespace() }
        val randomID = UUID.randomUUID().toString()
        val id = "${exmName}_${progName}_${randomID}"
        return id
    }

    fun generateNotificationID(id: String): Int{

        val sizeOfID = id.length
        val random = (0..1000).random()
        val id = sizeOfID + random

        return id
    }
}