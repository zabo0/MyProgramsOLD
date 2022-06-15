package com.saboon.myprograms.Adapters.SubjectProgram

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Fragments.SubjectProgram.MainFragmentDirections
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_MAIN_FRAGMENT

class MainFragmentChildRecyclerAdapter(private val subjects: List<ModelSubject>, private val subjectTimes: List<ModelSubjectTime>):RecyclerView.Adapter<MainFragmentChildRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val subjectNameText: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_subjectName)
        val lecturerNameText: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_lecturerName)
        val startTimeText: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_startTime)
        val finishTime: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_finishTime)
        val typeOfSubjectText: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_typeOfSubject)
        val classroomText: TextView = view.findViewById(R.id.subject_main_child_recycler_textView_classroom)
        val dividerColor: View = view.findViewById(R.id.subject_main_child_recycler_divider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_subject_program_main_child,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.startTimeText.text = subjectTimes[position].timeStart
        holder.finishTime.text = subjectTimes[position].timeFinish
        holder.subjectNameText.text = subjects[subjects.indexOfFirst { it.id == subjectTimes[position].belowSubjectID }].subjectName
        holder.lecturerNameText.text = subjects[subjects.indexOfFirst { it.id == subjectTimes[position].belowSubjectID }].lecturerName
        holder.typeOfSubjectText.text = subjectTimes[position].typeOfLesson
        holder.classroomText.text = subjectTimes[position].classRoom
        holder.dividerColor.setBackgroundColor(Color.parseColor(subjects[subjects.indexOfFirst { it.id == subjectTimes[position].belowSubjectID }].color))


        holder.itemView.setOnClickListener {view->
            val action = MainFragmentDirections.actionMainFragmentToSubjectDetailsFragment(subjects[subjects.indexOfFirst { it.id == subjectTimes[position].belowSubjectID }].id,
                FROM_MAIN_FRAGMENT)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return subjectTimes.size
    }
}