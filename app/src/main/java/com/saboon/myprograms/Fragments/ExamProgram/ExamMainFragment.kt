package com.saboon.myprograms.Fragments.ExamProgram

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Activities.MainActivity
import com.saboon.myprograms.Adapters.ExamProgram.ExamMainFragmentRecyclerAdapter
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Exam.ModelExamProgramMainSection
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.ExamVM.ExamMainViewModel
import com.saboon.myprograms.databinding.FragmentExamMainBinding


class ExamMainFragment : Fragment() {

    private var _binding: FragmentExamMainBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: ExamMainViewModel

    lateinit var program: ModelProgram
    lateinit var examList: List<ModelExam>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ExamMainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_exam_main, container, false)
        _binding = FragmentExamMainBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLastProgramID(SHARED_PREF_ID) {
            if (it) {
                viewModel.sharedPref.observe(viewLifecycleOwner, Observer { sharedPref ->
                    if (sharedPref != null) {
                        viewModel.getData(sharedPref.lastProgramID)
                    }
                })
            }
        }

        binding.examMainRecycler.layoutManager = LinearLayoutManager(context)

        observer()
        buttons()
    }


    private fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                program = it
                binding.topAppBar.title = program.name
                when(program.typeOfProgram){
                    PROGRAM_SUBJECT -> {binding.topAppBar.subtitle = "Subject Program"}
                    PROGRAM_EXAM -> {binding.topAppBar.subtitle = "Exam Program"}
                    PROGRAM_DIET -> {binding.topAppBar.subtitle = "Diet Program"}
                }
            }
        })

        viewModel.exams.observe(viewLifecycleOwner, Observer {exams->
            if (exams != null){
                examList = exams
                binding.examMainRecycler.visibility = View.VISIBLE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE

                binding.examMainRecycler.adapter = ExamMainFragmentRecyclerAdapter(getDataToRecyclerView(exams))
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.examMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.empty.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.examMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.VISIBLE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.examMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.VISIBLE
            }
        })
    }

    private fun buttons(){

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.goToAllExams -> {
                    val actionToExams = ExamMainFragmentDirections.actionExamMainFragmentToAllExamsFragment(program.id)
                    findNavController().navigate(actionToExams)
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.buttonAddNewExam.setOnClickListener {
            val action = ExamMainFragmentDirections.actionExamMainFragmentToAddEditExamFragment(program.id,null)
            it.findNavController().navigate(action)
        }
    }


    private fun getDataToRecyclerView(exams:List<ModelExam>):List<ModelExamProgramMainSection>{
        val sectionList: ArrayList<ModelExamProgramMainSection> = arrayListOf()

        var fromIndex = 0
        var toIndex = 0

        while (fromIndex < exams.size){
            while (exams[fromIndex].day == exams[toIndex].day){
                toIndex++
                if (toIndex>=exams.size){
                    break
                }
            }
            val examListToSection: ArrayList<ModelExam> = arrayListOf()
            val subExams = exams.subList(fromIndex,toIndex)
            examListToSection.addAll(subExams)

            if (examListToSection.isNotEmpty()){
                val section = ModelExamProgramMainSection(DayConverter().getDay(exams[fromIndex].day),examListToSection)
                sectionList.add(section)
            }

            fromIndex = toIndex
        }

        return sectionList
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}