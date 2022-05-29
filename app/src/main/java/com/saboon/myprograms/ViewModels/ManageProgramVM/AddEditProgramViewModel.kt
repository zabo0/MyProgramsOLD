package com.saboon.myprograms.ViewModels.ManageProgramVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditProgramViewModel(application: Application): BaseViewModel(application) {

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

    fun updateProgram(programID: String, newName:String, newDateEdited:String, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).programDAO().updateProgram(programID,newName,newDateEdited)
            callback(true)
        }
    }
}