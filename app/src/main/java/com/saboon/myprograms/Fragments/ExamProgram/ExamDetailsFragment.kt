package com.saboon.myprograms.Fragments.ExamProgram

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectDetailsFragmentArgs
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectDetailsFragmentDirections
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.ExamVM.ExamDetailsViewModel
import com.saboon.myprograms.databinding.FragmentExamDetailsBinding
import com.saboon.myprograms.databinding.FragmentExamMainBinding


class ExamDetailsFragment : Fragment() {

    private var _binding: FragmentExamDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: ExamDetailsViewModel

    lateinit var exam:ModelExam
    lateinit var program:ModelProgram

    private var from = 0

    lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExamDetailsViewModel::class.java)
        window = requireActivity().window
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_exam_details, container, false)
        _binding = FragmentExamDetailsBinding.inflate(inflater , container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            if (it!=null){
                val examID = ExamDetailsFragmentArgs.fromBundle(it).examID
                viewModel.getExam(examID)

                from = ExamDetailsFragmentArgs.fromBundle(it).from

            }
        }



        observer()
        buttons()
    }


    private fun observer(){
        viewModel.exam.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                exam = it
                makeTheme(exam.color)
                binding.examDetailsTextViewExamName.text = exam.examName
                binding.examDetailsTextViewWhichDay.text = requireContext().resources.getStringArray(R.array.Days)[exam.day.toInt()]
                binding.examDetailsTextViewClassroom.text = exam.classroom
                binding.examDetailsTextViewStartTime.text = exam.timeStart
                binding.examDetailsTextViewFinishTime.text = exam.timeFinish
                binding.examDetailsTextViewTargetPoint.text = exam.targetPoint
                binding.examDetailsTextViewTypeOfExam.text = exam.typeOfExam
                binding.examDetailsTextViewReminder.text = requireContext().resources.getStringArray(R.array.reminder)[exam.reminderTime.toInt()]
                if (exam.isDone){
                    binding.layoutIsExamDone.visibility = View.VISIBLE
                    binding.point.text = exam.point
                    if (exam.point.toInt() > exam.targetPoint.toInt()){
                        binding.pointText.setTextColor(Color.parseColor("#13FF00"))
                        binding.point.setTextColor(Color.parseColor("#13FF00"))
                    }else if(exam.point.toInt() < exam.targetPoint.toInt()){
                        binding.pointText.setTextColor(Color.parseColor("#FF0000"))
                        binding.point.setTextColor(Color.parseColor("#FF0000"))
                    }
                }else{
                    binding.layoutIsExamDone.visibility = View.GONE
                }
            }
        })
    }

    private fun buttons(){
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.editSubject->{
                    val action = ExamDetailsFragmentDirections.actionExamDetailsFragmentToAddEditExamFragment(exam.belowProgramID,exam.id)
                    findNavController().navigate(action)
                    true
                }
                R.id.deleteSubject->{
                    ShowAlertDialog(requireActivity(),requireContext()).showDeleteAlert("Delete","Are you sure to delete?"){
                        if (it){
                            viewModel.deleteExam(exam.id){
                                if(it){
                                    val action = ExamDetailsFragmentDirections.actionExamDetailsFragmentToAllExamsFragment(exam.belowProgramID)
                                    findNavController().navigate(action)
                                }
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            when(from){
                FROM_MAIN_FRAGMENT -> {
                    val action = ExamDetailsFragmentDirections.actionExamDetailsFragmentToExamMainFragment()
                    it.findNavController().navigate(action)
                }
                FROM_ALL_EXAMS_FRAGMENT -> {
                    val action = ExamDetailsFragmentDirections.actionExamDetailsFragmentToAllExamsFragment(exam.belowProgramID)
                    it.findNavController().navigate(action)
                }
                FROM_ADD_EDIT_EXAM_FRAGMENT -> {
                    val action = ExamDetailsFragmentDirections.actionExamDetailsFragmentToExamMainFragment()
                    it.findNavController().navigate(action)
                }
            }
        }
    }


    private fun makeTheme(color: String){
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                for (drawable in binding.examDetailsTextViewExamName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsWhichDay.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsClassroom.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsStartTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsFinishTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsTargetPoint.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsTypeOfExam.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsReminder.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                binding.view.setBackgroundColor(Color.parseColor(color))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                for (drawable in binding.examDetailsTextViewExamName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsWhichDay.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsClassroom.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsStartTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsFinishTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsTargetPoint.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsTypeOfExam.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.examDetailsReminder.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                binding.view.setBackgroundColor(Color.parseColor(color))
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}