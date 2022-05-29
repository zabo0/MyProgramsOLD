package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class SubjectsFragmentViewModel(application: Application): BaseViewModel(application) {

    var subjects = MutableLiveData<List<ModelSubject>?>()
    var loading = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()


    fun getAllSubject(){
        loading.value = true
        launch {
            val subjectList = DatabaseMyPrograms(getApplication()).subjectDAO().getAllSubjects()

            if (subjectList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                subjects.value = subjectList

                loading.value = false
                empty.value = false
                error.value = false
            }
        }
    }

    fun getAllSubjectByFilter(filter:String){
        loading.value = true
        launch {
            val subjectList = DatabaseMyPrograms(getApplication()).subjectDAO().getAllSubjectByFilter(filter)

            if (subjectList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                subjects.value = subjectList

                loading.value = false
                empty.value = false
                error.value = false
            }
        }
    }
}