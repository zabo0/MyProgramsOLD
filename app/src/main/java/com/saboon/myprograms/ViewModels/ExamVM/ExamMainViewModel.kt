package com.saboon.myprograms.ViewModels.ExamVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.ModelSharedPref
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class ExamMainViewModel(application: Application):BaseViewModel(application) {


    val exams = MutableLiveData<List<ModelExam>?>()
    val program = MutableLiveData<ModelProgram>()
    val sharedPref = MutableLiveData<ModelSharedPref?>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    fun getData(programID: String){
        loading.value = true
        launch {
            program.value = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            val examList = DatabaseMyPrograms(getApplication()).examDAO().getAllExams(programID)


            if (examList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                exams.value = examList

                loading.value = false
                empty.value = false
                error.value = false
            }
        }
    }

    fun getLastProgramID(sharedPrefID:String, callback:(Boolean) -> Unit){
        launch {
            sharedPref.value = DatabaseMyPrograms(getApplication()).sharedPrefDAO().getSharedPref(sharedPrefID)
            callback(true)
        }
    }

}