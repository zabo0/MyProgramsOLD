package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditSubjectTimeFragmentViewModel(application: Application):BaseViewModel(application) {


    val subjectTime = MutableLiveData<ModelSubjectTime>()
    val subject = MutableLiveData<ModelSubject>()
    val program = MutableLiveData<ModelProgram>()


    fun getSubjectTime(subjectTimeID:String){
        launch {
            val sbjTime = DatabaseMyPrograms(getApplication()).subjectTimeDAO().getSubjectTime(subjectTimeID)
            subjectTime.value = sbjTime
        }
    }

    fun getProgram(programID: String){
        launch {
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            program.value = prog
        }
    }

    fun getSubject(subjectID: String){
        launch {
            val sbj = DatabaseMyPrograms(getApplication()).subjectDAO().getSubject(subjectID)
            subject.value = sbj
        }
    }

    fun saveSubjectTime(subjectTime: ModelSubjectTime, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectTimeDAO().insert(subjectTime)
            callback(true)
        }
    }

    fun deleteSubjectTime(subjectTimeID: String, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectTimeDAO().deleteSubjectTime(subjectTimeID)
            callback(true)
        }
    }

    fun updateSubjectTime(subjectTime: ModelSubjectTime, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectTimeDAO().updateSubjectTime(
                subjectTime.id,
                subjectTime.day!!,
                subjectTime.timeStart!!,
                subjectTime.timeFinish!!,
                subjectTime.typeOfLesson!!,
                subjectTime.classRoom!!,
                subjectTime.reminderTime!!,
                subjectTime.belowSubjectID,
                subjectTime.belowProgramID
            )
            callback(true)
        }
    }

}