package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import kotlinx.coroutines.launch

class AddEditProgramViewModel(application: Application):BaseViewModel(application) {

    val program = MutableLiveData<ModelProgram>()



    fun storeProgram(program: ModelProgram,  callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).programDAO().insertProgram(program)
            callback(true)
        }
    }

    fun getProgram(programID: String){
        launch {
            program.value = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
        }
    }

    fun updateProgram(){

    }
}