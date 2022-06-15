package com.saboon.myprograms.ViewModels.SubjectVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.ModelSharedPref
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application):BaseViewModel(application) {

    val program = MutableLiveData<ModelProgram>()
    val subjects = MutableLiveData<List<ModelSubject>?>()
    val subjectTimes = MutableLiveData<List<ModelSubjectTime>?>()
    val sharedPref = MutableLiveData<ModelSharedPref?>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()



    fun getData(programID:String){
        loading.value = true
        launch {
            program.value = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            val subjectList = DatabaseMyPrograms(getApplication()).subjectDAO().getAllSubjects(programID)
            val subjectTimeList = DatabaseMyPrograms(getApplication()).subjectTimeDAO().getAllSubjectTimes(programID)


            if (subjectList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                subjects.value = subjectList
                subjectTimes.value = subjectTimeList

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

    fun setLastProgramID(sharedPref: ModelSharedPref, callback: (Boolean) -> Unit){
        deleteLastProgram(sharedPref)
        launch {
            DatabaseMyPrograms(getApplication()).sharedPrefDAO().insert(sharedPref)
            callback(true)
        }
    }

    fun deleteLastProgram(sharedPref: ModelSharedPref){
        launch {
            DatabaseMyPrograms(getApplication()).sharedPrefDAO().deleteLastProgramID(sharedPref.id)
        }
    }


}