package com.saboon.myprograms.Adapters.ExamProgram

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.saboon.myprograms.Adapters.SubjectProgram.MainFragmentChildRecyclerAdapter
import com.saboon.myprograms.Fragments.ExamProgram.ExamMainFragmentDirections
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectMainFragmentDirections
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_MAIN_FRAGMENT

class ExamMainFragmentChildRecyclerAdapter(private val exams: List<ModelExam>):RecyclerView.Adapter<ExamMainFragmentChildRecyclerAdapter.ExamMainFragmentChildRecyclerAdapterViewHolder>() {
    class ExamMainFragmentChildRecyclerAdapterViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val examNameText: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_examName)
        val targetPoint: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_targetPoint)
        val point: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_point)
        val pointText: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_pointText)
        val layoutPoint: LinearLayout = view.findViewById(R.id.layoutPoint)
        val startTimeText: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_startTime)
        val finishTime: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_finishTime)
        val typeOfExamText: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_typeOfExam)
        val classroomText: TextView = view.findViewById(R.id.exam_main_child_recycler_textView_classroom)
        val dividerColor: View = view.findViewById(R.id.exam_main_child_recycler_divider)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExamMainFragmentChildRecyclerAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_exam_program_main_child,parent,false)
        return ExamMainFragmentChildRecyclerAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExamMainFragmentChildRecyclerAdapterViewHolder,
        position: Int
    ) {
        holder.examNameText.text = exams[position].examName
        holder.targetPoint.text = exams[position].targetPoint
        holder.startTimeText.text = exams[position].timeStart
        holder.finishTime.text = exams[position].timeFinish
        holder.typeOfExamText.text = exams[position].typeOfExam
        holder.classroomText.text = exams[position].classroom
        holder.dividerColor.setBackgroundColor(Color.parseColor(exams[position].color))

        if (exams[position].isDone){
            holder.layoutPoint.visibility = View.VISIBLE
            holder.point.text = exams[position].point
            if (exams[position].point.toInt() > exams[position].targetPoint.toInt()){
                holder.pointText.setTextColor(Color.parseColor("#13FF00"))
                holder.point.setTextColor(Color.parseColor("#13FF00"))
            }else if(exams[position].point.toInt() < exams[position].targetPoint.toInt()){
                holder.pointText.setTextColor(Color.parseColor("#FF0000"))
                holder.point.setTextColor(Color.parseColor("#FF0000"))
            }
        }

        holder.itemView.setOnClickListener {view->
            val action = ExamMainFragmentDirections.actionExamMainFragmentToExamDetailsFragment(exams[position].id,
                FROM_MAIN_FRAGMENT
            )
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return exams.size
    }
}