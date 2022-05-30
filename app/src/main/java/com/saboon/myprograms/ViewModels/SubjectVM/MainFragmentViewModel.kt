package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application):BaseViewModel(application) {

    val program = MutableLiveData<ModelProgram>()
    val subjects = MutableLiveData<List<ModelSubject>?>()
    val subjectTimes = MutableLiveData<List<ModelSubjectTime>?>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()



    fun getData(programID:String){
        loading.value = true
        launch {
            program.value = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            subjects.value = DatabaseMyPrograms(getApplication()).subjectDAO().getAllSubjects()
            subjectTimes.value


        }
    }


}