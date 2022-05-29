package com.saboon.myprograms.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelStates

class ManageProgramsViewModel(app: Application): BaseViewModel(app) {


    val programs = MutableLiveData <List<ModelProgram>?>()
    val states = MutableLiveData<ModelStates>()



    fun storeProgram(){

    }

    fun getAllPrograms(){

    }

    fun getAllProgramsByFilter(){

    }

    fun getProgram(){

    }

    fun deleteProgram(){

    }

}