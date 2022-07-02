package com.saboon.myprograms.ViewModels.ExamVM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.saboon.myprograms.Database.DatabaseMyPrograms
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.ViewModels.BaseViewModel
import kotlinx.coroutines.launch

class AddEditExamViewModel(application: Application): BaseViewModel(application) {

    val exam = MutableLiveData<ModelExam>()
    val program = MutableLiveData<ModelProgram>()


    fun getExam(examID: String){
        launch{
            val exm = DatabaseMyPrograms(getApplication()).examDAO().getExam(examID)
            exam.value = exm
        }
    }

    fun getProgram(programID: String){
        launch {
            val prog = DatabaseMyPrograms(getApplication()).programDAO().getProgram(programID)
            program.value = prog
        }
    }

    fun saveNewExam(exam: ModelExam, callback:(Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).examDAO().insert(exam)
            callback(true)
        }
    }

    fun updateExam(exam: ModelExam, callback: (Boolean) -> Unit){
        launch {
            DatabaseMyPrograms(getApplication()).examDAO().updateExam(

                exam.id,
                exam.examName!!,
                exam.color,
                exam.day,
                exam.timeStart!!,
                exam.timeFinish!!,
                exam.typeOfExam!!,
                exam.classroom!!,
                exam.targetPoint,
                exam.point,
                exam.isDone,
                exam.reminderTime,
                exam.dateEdited
            )
            callback(true)
        }
    }

}