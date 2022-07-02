package com.saboon.myprograms.Adapters.ExamProgram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Fragments.ExamProgram.AllExamsFragment
import com.saboon.myprograms.Fragments.ExamProgram.AllExamsFragmentDirections
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.DayConverter
import com.saboon.myprograms.Utils.FROM_ALL_EXAMS_FRAGMENT
import com.saboon.myprograms.Utils.FROM_MAIN_FRAGMENT

class AllExamsFragmentRecyclerAdapter(val examList:List<ModelExam>): RecyclerView.Adapter<AllExamsFragmentRecyclerAdapter.AllExamFragmentViewHolder>() {
    class AllExamFragmentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val examName: TextView = view.findViewById(R.id.exam_allExams_recycler_textView_examName)
        val targetPoint: TextView = view.findViewById(R.id.exam_allExams_recycler_textView_targetPoint)
        val dateEdited: TextView = view.findViewById(R.id.exam_allExams_recycler_textView_dateEdited)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllExamFragmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_exam_program_all_exams,parent,false)
        return AllExamFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllExamFragmentViewHolder, position: Int) {
        holder.examName.text = examList[position].examName
        holder.targetPoint.text = examList[position].targetPoint
        holder.dateEdited.text = DayConverter().getDay(examList[position].dateEdited)


        holder.itemView.setOnClickListener {
            val action = AllExamsFragmentDirections.actionAllExamsFragmentToExamDetailsFragment(examList[position].id,
                FROM_ALL_EXAMS_FRAGMENT)
            it.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return examList.size
    }
}