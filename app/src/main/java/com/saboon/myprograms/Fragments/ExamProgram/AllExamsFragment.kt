package com.saboon.myprograms.Fragments.ExamProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.ExamProgram.AllExamsFragmentRecyclerAdapter
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectsFragmentRecyclerAdapter
import com.saboon.myprograms.Fragments.SubjectProgram.SubjectsFragmentDirections
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.ViewModels.ExamVM.AllExamsViewModel
import com.saboon.myprograms.databinding.FragmentAllExamsBinding
import com.saboon.myprograms.databinding.FragmentSubjectsBinding

class AllExamsFragment : Fragment() {

    private var _binding: FragmentAllExamsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel : AllExamsViewModel

    lateinit var examList: List<ModelExam>
    lateinit var programID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get((AllExamsViewModel::class.java))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_all_exams, container, false)
        _binding = FragmentAllExamsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {
            if (it!=null){
                programID = AllExamsFragmentArgs.fromBundle(it).programID
            }
        }

        viewModel.getAllExams(programID)
        binding.examsRecycler.layoutManager = LinearLayoutManager(context)
        observer()
        buttons()
    }

    fun observer(){
        viewModel.exams.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if(it != null) {
                    examList = it
                    binding.examsRecycler.visibility = View.VISIBLE
                    binding.progressBarLoading.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    binding.linearLayoutError.visibility = View.GONE
                    binding.examsRecycler.adapter = AllExamsFragmentRecyclerAdapter(examList)
                    binding.examsRecycler.addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.examsRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.empty.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.examsRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.VISIBLE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.examsRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.VISIBLE
            }
        })
    }

    private fun buttons(){
        binding.fab.setOnClickListener {
            val actionToNew = AllExamsFragmentDirections.actionAllExamsFragmentToAddEditExamFragment(programID,null)
            it.findNavController().navigate(actionToNew)
        }

        binding.topAppBar.setNavigationOnClickListener {
            val action = AllExamsFragmentDirections.actionAllExamsFragmentToExamMainFragment()
            it.findNavController().navigate(action)
        }

        binding.buttonAddNewExam.setOnClickListener {
            val actionToNew = AllExamsFragmentDirections.actionAllExamsFragmentToAddEditExamFragment(programID,null)
            it.findNavController().navigate(actionToNew)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}