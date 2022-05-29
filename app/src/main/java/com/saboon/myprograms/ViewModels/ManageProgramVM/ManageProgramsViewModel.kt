package com.saboon.myprograms.ViewModels.ManageProgramVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class ManageProgramsViewModel(application: Application): BaseViewModel(application) {


    var programs = MutableLiveData <List<ModelProgram>?>()
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

    fun getAllProgramsByFilter(filter: String){
        loading.value = true
        launch {
            val programList = DatabaseMyPrograms(getApplication()).programDAO().getAllProgramByFilter(filter)

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

}