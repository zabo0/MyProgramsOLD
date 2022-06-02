package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditSubjectFragmentViewModel(application: Application): BaseViewModel(application) {

    val subject = MutableLiveData<ModelSubject>()


    fun getSubject(subjectID:String){
        launch {
            val sbj = DatabaseMyPrograms(getApplication()).subjectDAO().getSubject(subjectID)
            subject.value = sbj
        }
    }


}