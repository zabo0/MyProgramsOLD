package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditSubjectFragmentViewModel(application: Application): BaseViewModel(application) {

    val subject = MutableLiveData<ModelSubject>()
    val program = MutableLiveData<ModelProgram>()


    fun getSubject(subjectID:String){
        launch {
            val sbj = DatabaseMyPrograms(getApplication()).subjectDAO().getSubject(subjectID)
            subject.value = sbj
        }
    }

    fun getProgram(programID: String){
        launch {
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            program.value = prog
        }
    }

    fun saveNewSubject(subject: ModelSubject, callback:(Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectDAO().insert(subject)
            callback(true)
        }
    }

    fun updateSubject(subject: ModelSubject, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectDAO().updateSubject(
                subject.id,
                subject.subjectName!!,
                subject.lecturerName!!,
                subject.color,
                subject.absenteeism,
                subject.dateEdited
            )
            callback(true)
        }
    }


}