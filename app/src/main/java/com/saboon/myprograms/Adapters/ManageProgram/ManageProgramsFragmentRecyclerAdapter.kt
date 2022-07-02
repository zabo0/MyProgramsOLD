package com.saboon.myprograms.Adapters.ManageProgram

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Activities.ExamProgramActivity
import com.saboon.myprograms.Activities.SubjectProgramActivity
import com.saboon.myprograms.Fragments.ManageProgram.ManageProgramsFragmentDirections
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.ModelSharedPref
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.SubjectVM.MainFragmentViewModel
import java.text.SimpleDateFormat

class ManageProgramsFragmentRecyclerAdapter(private val programList: List<ModelProgram>, val application: Application):RecyclerView.Adapter<ManageProgramsFragmentRecyclerAdapter.ManageProgramViewHolder>() {

    class ManageProgramViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val programName: TextView = view.findViewById(R.id.manage_recycler_textView_programName)
        val editedDate: TextView = view.findViewById(R.id.manage_recycler_textView_dateEdited)
        val goToDetails: LinearLayout = view.findViewById(R.id.linearLayout_goToDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_manage_program,parent,false)
        return ManageProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManageProgramViewHolder, position: Int) {
        holder.programName.text = programList[position].name

        holder.editedDate.text = DayConverter().getDay(programList[position].dateEdited)

        holder.goToDetails.setOnClickListener {
            val action = ManageProgramsFragmentDirections.actionManageProgramsFragmentToDetailsProgramFragment(programList[position].id)
            it.findNavController().navigate(action)
        }

        holder.itemView.setOnClickListener {view ->

            val sharedPref = ModelSharedPref(SHARED_PREF_ID, programList[position].id)
            MainFragmentViewModel(application).setLastProgramID(sharedPref){
                if (it){
                    when(programList[position].typeOfProgram){
                        PROGRAM_SUBJECT->{
                            val intent = Intent(view.context, SubjectProgramActivity::class.java)
                            view.context.startActivity(intent)
                            (holder.itemView.context as Activity).finish()
                        }
                        PROGRAM_EXAM -> {
                            val intent = Intent(view.context, ExamProgramActivity::class.java)
                            view.context.startActivity(intent)
                            (holder.itemView.context as Activity).finish()
                        }
                        PROGRAM_DIET ->{
                            //intent to diet
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return programList.size
    }
}