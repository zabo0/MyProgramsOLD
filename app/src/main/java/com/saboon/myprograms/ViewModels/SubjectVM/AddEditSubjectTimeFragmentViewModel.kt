package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditSubjectTimeFragmentViewModel(application: Application):BaseViewModel(application) {


    val subjectTime = MutableLiveData<ModelSubjectTime>()
    val subject = MutableLiveData<ModelSubject>()


    fun getSubjectTime(subjectTimeID:String){
        launch {
            val sbjTime = DatabaseMyPrograms(getApplication()).subjectTimeDAO().getSubjectTime(subjectTimeID)
            subjectTime.value = sbjTime
        }
    }

    fun getSubject(subjectID: String){
        launch {
            val sbj = DatabaseMyPrograms(getApplication()).subjectDAO().getSubject(subjectID)
            subject.value = sbj
        }
    }

}