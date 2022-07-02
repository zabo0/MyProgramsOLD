package com.saboon.myprograms.Adapters.ExamProgram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Adapters.SubjectProgram.MainFragmentChildRecyclerAdapter
import com.saboon.myprograms.Models.Exam.ModelExamProgramMainSection
import com.saboon.myprograms.Models.Subject.ModelSubjectProgramMainSection
import com.saboon.myprograms.R

class ExamMainFragmentRecyclerAdapter(private val sectionList: List<ModelExamProgramMainSection>) :
    RecyclerView.Adapter<ExamMainFragmentRecyclerAdapter.ExamMainFragmentRecyclerAdapterViewHolder>() {
    class ExamMainFragmentRecyclerAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sectionText: TextView = view.findViewById(R.id.sectionDay)
        val childRecycler: RecyclerView = view.findViewById(R.id.childRecyclerView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExamMainFragmentRecyclerAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_row_section_days, parent, false)
        return ExamMainFragmentRecyclerAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExamMainFragmentRecyclerAdapterViewHolder,
        position: Int
    ) {
        holder.sectionText.text = holder.itemView.context.resources.getStringArray(R.array.Days)[sectionList[position].day]

        holder.childRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.childRecycler.adapter = ExamMainFragmentChildRecyclerAdapter(sectionList[position].exams)
        holder.childRecycler.addItemDecoration(
            DividerItemDecoration(holder.itemView.context, DividerItemDecoration.VERTICAL)
        )
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }
}