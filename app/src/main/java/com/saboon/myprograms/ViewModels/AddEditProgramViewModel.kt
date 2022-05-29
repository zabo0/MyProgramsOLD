package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Models.ModelProgram

class AddEditProgramViewModel(application: Application):BaseViewModel(application) {

    val program = MutableLiveData<ModelProgram>()



    fun storeProgram(){

    }

    fun updateProgram(){

    }
}