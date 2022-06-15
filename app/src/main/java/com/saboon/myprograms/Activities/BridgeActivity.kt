package com.saboon.myprograms.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.PROGRAM_DIET
import com.saboon.myprograms.Utils.PROGRAM_EXAM
import com.saboon.myprograms.Utils.PROGRAM_SUBJECT
import com.saboon.myprograms.Utils.SHARED_PREF_ID
import com.saboon.myprograms.ViewModels.ManageProgramVM.ManageProgramsViewModel

class BridgeActivity : AppCompatActivity() {

    lateinit var viewModel: ManageProgramsViewModel

    //bu activity uygulama ilk basladiginda burada en son acilan program kontrol edilir ve ona gore uygun activity e yonlendirilir


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ManageProgramsViewModel::class.java)

        viewModel.getLastProgramID(SHARED_PREF_ID) {
            if (it) {
                viewModel.sharedPref.observe(this, Observer { sharedPref ->
                    if (sharedPref != null) {
                        viewModel.getProgram(sharedPref.lastProgramID) { callback ->
                            if (callback) {
                                viewModel.program.observe(this, Observer { program ->
                                    if (program != null) {
                                        when (program.typeOfProgram) {
                                            PROGRAM_SUBJECT -> {
                                                val intent =
                                                    Intent(this, SubjectProgramActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                            PROGRAM_EXAM -> {
                                                //INTENT TO EXAM
                                            }
                                            PROGRAM_DIET -> {
                                                //intent to diet
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }else{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
            }
        }
    }
}