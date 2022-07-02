package com.saboon.myprograms.Adapters.SubjectProgram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Models.Subject.ModelSubjectProgramMainSection
import com.saboon.myprograms.R

class MainFragmentRecyclerAdapter(private val sectionList: ArrayList<ModelSubjectProgramMainSection>):RecyclerView.Adapter<MainFragmentRecyclerAdapter.MainFragmentViewHolder>() {

    class MainFragmentViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val sectionText: TextView = view.findViewById(R.id.sectionDay)
        val childRecycler: RecyclerView = view.findViewById(R.id.childRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_section_days,parent,false)
        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {

        holder.sectionText.text = holder.itemView.context.resources.getStringArray(R.array.Days)[sectionList[position].day]


        holder.childRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.childRecycler.adapter = MainFragmentChildRecyclerAdapter(sectionList[position].subjects, sectionList[position].subjectTimes)
        holder.childRecycler.addItemDecoration(
            DividerItemDecoration(holder.itemView.context, DividerItemDecoration.VERTICAL)
        )



    }

    override fun getItemCount(): Int {
        return sectionList.size
    }
}