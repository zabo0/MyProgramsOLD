package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.saboon.myprograms.R
import com.saboon.myprograms.ViewModels.SubjectVM.AddEditSubjectFragmentViewModel
import com.saboon.myprograms.databinding.FragmentAddEditSubjectBinding


class AddEditSubjectFragment : Fragment() {


    private var _binding: FragmentAddEditSubjectBinding ?= null
    private val binding get() = _binding!!

    lateinit var viewModel: AddEditSubjectFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_edit_subject, container, false)
        _binding = FragmentAddEditSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddEditSubjectFragmentViewModel::class.java)




    }


    fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if( it!= null){
                binding.subjectAddEditSubjectEditTextSubjectName.setText(it.subjectName)
                binding.subjectAddEditSubjectEditTextLecturerName.setText(it.lecturerName)
                binding.subjectAddEditSubjectEditTextMaxAbsenteeism.setText(it.absenteeism)
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}