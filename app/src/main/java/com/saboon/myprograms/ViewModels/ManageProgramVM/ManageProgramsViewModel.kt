package com.saboon.myprograms.ViewModels.ManageProgramVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelSharedPref
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class ManageProgramsViewModel(application: Application): BaseViewModel(application) {


    var programs = MutableLiveData <List<ModelProgram>?>()
    var program = MutableLiveData<ModelProgram>()
    val sharedPref = MutableLiveData<ModelSharedPref?>()
    var loading = MutableLiveData<Boolean>()
    var empty = MutableLiveData<Boolean>()
    var error = MutableLiveData<Boolean>()

    fun getAllPrograms(){
        loading.value = true

        launch {
            val programList = DatabaseMyPrograms(getApplication()).programDAO().getAllPrograms()

            if (programList.isEmpty()){
                loading.value = false
                empty.value = true
                error.value = false
            }else{
                programs.value = programList

                loading.value = false
                empty.value = false
                error.value = false
            }
        }
    }

    fun getProgram(programID:String, callback: (Boolean) -> Unit){
        launch {
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            program.value = prog
            callback(true)
        }
    }

    fun getLastProgramID(sharedPrefID:String, callback:(Boolean) -> Unit){
        launch {
            sharedPref.value = DatabaseMyPrograms(getApplication()).sharedPrefDAO().getSharedPref(sharedPrefID)
            callback(true)
        }
    }


}