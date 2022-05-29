package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates

class ManageProgramsViewModel(application: Application): BaseViewModel(application) {


    val programs = MutableLiveData <List<ModelProgram>?>()
    val states = MutableLiveData<ModelStates>()

    fun getAllPrograms(){

    }

    fun getAllProgramsByFilter(){

    }

}