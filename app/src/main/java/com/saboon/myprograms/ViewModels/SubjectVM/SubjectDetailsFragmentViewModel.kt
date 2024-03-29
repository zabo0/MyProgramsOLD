package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class SubjectDetailsFragmentViewModel(application: Application):BaseViewModel(application) {

    var subjectTimes = MutableLiveData<List<ModelSubjectTime>?>()
    var subject = MutableLiveData<ModelSubject>()
    var program = MutableLiveData<ModelProgram>()



    fun getSubject(subjectID:String){
        launch {
            val sbj = DatabaseMyPrograms(getApplication()).subjectDAO().getSubject(subjectID)
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(sbj.belowProgram)
            subject.value = sbj
            program.value = prog
        }

    }

    fun getSubjectTimes(subjectID: String){
        launch {
            val sbjTimes = DatabaseMyPrograms(getApplication()).subjectTimeDAO().getSubjectSubjectsTime(subjectID)
            if (sbjTimes.isNotEmpty()){
                subjectTimes.value = sbjTimes
            }
        }
    }

    fun deleteSubject(subjectID: String, callback:(Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).subjectDAO().deleteSubject(subjectID)
            callback(true)
        }
    }

    fun deleteSubjectTimes(subjectID: String){
        launch {
            DatabaseMyPrograms(getApplication()).subjectTimeDAO().deleteSubjectSubjectTimes(subjectID)
        }
    }




}