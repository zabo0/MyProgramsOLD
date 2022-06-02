package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.ViewModels.SubjectVM.SubjectDetailsFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding

class SubjectDetailsFragment : Fragment() {


    private var _binding: FragmentSubjectDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: SubjectDetailsFragmentViewModel

    lateinit var subject: ModelSubject
    lateinit var program: ModelProgram


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_subject_details, container, false)
        _binding = FragmentSubjectDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SubjectDetailsFragmentViewModel::class.java)

        arguments.let {
            if (it!=null){
                val subjectID = it.getString("subjectID").toString()
                viewModel.getSubject(subjectID)
            }
        }


        observer()
        buttons()
    }


    private fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subject = it
                binding.subjectDetailsTextViewSubjectName.text = subject.subjectName
                binding.subjectDetailsTextViewLecturerName.text = subject.lecturerName
                binding.subjectDetailsTextViewAbsenteeism.text = subject.absenteeism
            }
        })
    }

    private fun buttons(){
        binding.subjectDetailsImageViewEdit.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectFragment(subject.belowProgram,subject.id)
            it.findNavController().navigate(action)
        }

        binding.subjectDetailsImageViewDelete.setOnClickListener { view ->
            viewModel.deleteSubject(subject.id){
                if(it){
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                    view.findNavController().navigate(action)
                }
            }
        }

        binding.subjectDetailsTextViewSubjectNameGotoBack.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}