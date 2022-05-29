package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates

class DetailsProgramViewModel(application: Application):BaseViewModel(application) {

    val program = MutableLiveData<ModelProgram>()
    val states = MutableLiveData<ModelStates>()


    fun getProgram(){

    }

    fun storeProgram(){

    }

    fun deleteProgram(){

    }

}