package com.saboon.myprograms.Adapters.SubjectProgram

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectDetailsFragmentDirections
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.R

class SubjectDetailsFragmentRecyclerAdapter(private val subjectTimeList: List<ModelSubjectTime>, private val color: String): RecyclerView.Adapter<SubjectDetailsFragmentRecyclerAdapter.DetailsViewHolder>() {
    class DetailsViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val countText:TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_count)
        val dayText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_day)
        val timeStartText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_timeStart)
        val timeFinishText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_timeFinish)
        val classroomText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_class)
        val typeOfSubjectText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_typeOfLesson)
        val reminderText: TextView = view.findViewById(R.id.subject_details_recyclerRow_textView_reminder)
        val divider: LinearLayout = view.findViewById(R.id.verticalDivider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_subject_program_subject_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val pos = position + 1
        holder.countText.text = pos.toString()
        holder.dayText.text = holder.itemView.context.resources.getStringArray(R.array.Days)[subjectTimeList[position].day!!.toInt()]
        holder.timeStartText.text = subjectTimeList[position].timeStart
        holder.timeFinishText.text = subjectTimeList[position].timeFinish
        holder.classroomText.text = subjectTimeList[position].classRoom
        holder.typeOfSubjectText.text = subjectTimeList[position].typeOfLesson
        holder.reminderText.text = holder.itemView.context.resources.getStringArray(R.array.reminder)[subjectTimeList[position].reminderTime!!.toInt()]
        holder.divider.setBackgroundColor(Color.parseColor(color))


        holder.itemView.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectTimeFragment(
                subjectTimeList[position].belowSubjectID,
                subjectTimeList[position].id
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return subjectTimeList.size
    }
}