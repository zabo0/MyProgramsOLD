package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.logging.Filter

class ManageProgramsViewModel(application: Application): BaseViewModel(application) {


    var programs = MutableLiveData <List<ModelProgram>?>()
    var states = MutableLiveData<ModelStates>()

    fun getAllPrograms(){
        states.value?.loading = true
        try {
            launch {
                val programList = DatabaseMyPrograms(getApplication()).programDAO().getAllPrograms()

                if (programList.isEmpty()){
                    states.value?.loading = false
                    states.value?.empty = true
                    states.value?.error = false
                }else{
                    programs.value = programList

                    states.value?.loading = false
                    states.value?.empty = false
                    states.value?.error = false
                }
            }
        }catch (e:Exception){
            states.value?.loading = false
            states.value?.empty = false
            states.value?.error = true
        }
    }

    fun getAllProgramsByFilter(filter: String){
        states.value?.loading = true
        try {
            launch {
                val programList = DatabaseMyPrograms(getApplication()).programDAO().getAllProgramByFilter(filter)

                if (programList.isEmpty()){
                    states.value?.loading = false
                    states.value?.empty = true
                    states.value?.error = false
                }else{
                    programs.value = programList

                    states.value?.loading = false
                    states.value?.empty = false
                    states.value?.error = false
                }
            }
        }catch (e:Exception){
            states.value?.loading = false
            states.value?.empty = false
            states.value?.error = true
        }
    }

}