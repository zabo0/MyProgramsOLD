package com.saboon.myprograms.ViewModels.ExamVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class ExamDetailsViewModel(application: Application):BaseViewModel(application) {

    var exam = MutableLiveData<ModelExam>()
    var program = MutableLiveData<ModelProgram>()


    fun getExam(examID:String){
        launch {
            val exm = DatabaseMyPrograms(getApplication()).examDAO().getExam(examID)
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(exm.belowProgramID)
            exam.value = exm
            program.value = prog
        }

    }

    fun deleteExam(examID: String, callback:(Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).examDAO().deleteExam(examID)
            callback(true)
        }
    }

}