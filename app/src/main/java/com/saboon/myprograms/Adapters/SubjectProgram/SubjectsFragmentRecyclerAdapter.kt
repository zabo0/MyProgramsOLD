package com.saboon.myprograms.Adapters.SubjectProgram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectsFragmentDirections
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_ALL_SUBJECT_FRAGMENT

class SubjectsFragmentRecyclerAdapter(val subjectList: List<ModelSubject>): RecyclerView.Adapter<SubjectsFragmentRecyclerAdapter.SubjectFragmentViewHolder>() {
    class SubjectFragmentViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val subjectName: TextView = view.findViewById(R.id.subject_main_recycler_textView_subjectName)
        val lecturerName: TextView = view.findViewById(R.id.subject_main_recycler_textView_lecturerName)
        val dateEdited : TextView = view.findViewById(R.id.subject_main_recycler_textView_dateEdited)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_subject_program_subjects,parent,false)
        return SubjectFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectFragmentViewHolder, position: Int) {
        holder.lecturerName.text = subjectList[position].lecturerName
        holder.subjectName.text = subjectList[position].subjectName
        holder.dateEdited.text = subjectList[position].dateEdited

        holder.itemView.setOnClickListener {
            val action = SubjectsFragmentDirections.actionSubjectsFragmentToSubjectDetailsFragment(subjectList[position].id,
                FROM_ALL_SUBJECT_FRAGMENT)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }
}