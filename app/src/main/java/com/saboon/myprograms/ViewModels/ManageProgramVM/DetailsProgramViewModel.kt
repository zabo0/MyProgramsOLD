package com.saboon.myprograms.ViewModels.ManageProgramVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class DetailsProgramViewModel(application: Application): BaseViewModel(application) {

    var program = MutableLiveData<ModelProgram>()


    fun getProgram(programID: String){
        launch {
            program.value = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
        }
    }

    fun deleteProgram(programID: String){
        launch {
            DatabaseMyPrograms(getApplication()).programDAO().deleteProgram(programID)
        }
    }

}