package com.saboon.myprograms.ViewModels.ExamVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AllExamsViewModel(application: Application):BaseViewModel(application) {
    var exams = MutableLiveData<List<ModelExam>?>()
    var loading = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()

    fun getAllExams(belowProgramID: String){
        loading.value = true
        launch {
            val examsList = DatabaseMyPrograms(getApplication()).examDAO().getAllExams(belowProgramID)

            if (examsList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                exams.value = examsList
                loading.value = false
                empty.value = false
                error.value = false
            }
        }
    }

}