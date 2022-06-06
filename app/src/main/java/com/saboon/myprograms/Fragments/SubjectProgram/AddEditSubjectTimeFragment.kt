package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.R
import com.saboon.myprograms.ViewModels.SubjectVM.AddEditSubjectTimeFragmentViewModel
import com.saboon.myprograms.databinding.FragmentAddEditSubjectTimeBinding


class AddEditSubjectTimeFragment : Fragment() {


    private var _binding: FragmentAddEditSubjectTimeBinding?=null
    private val binding get() = _binding!!


    lateinit var viewModel: AddEditSubjectTimeFragmentViewModel
    lateinit var subject: ModelSubject
    lateinit var subjectTime: ModelSubjectTime


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_edit_subject_time, container, false)
        _binding = FragmentAddEditSubjectTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(AddEditSubjectTimeFragmentViewModel::class.java)


        arguments.let {
            if (it!=null){
                AddEditSubjectTimeFragmentArgs.fromBundle(it).subjectID.let {
                    viewModel.getSubject(it)
                }

                AddEditSubjectTimeFragmentArgs.fromBundle(it).subjectTimeID.let {
                    if (it != null) {
                        viewModel.getSubjectTime(it)
                    }
                }
            }
        }

        observer()
    }


    private fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if (it != null){
                subject = it
            }
        })

        viewModel.subjectTime.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subjectTime = it
            }
        })
    }

    private fun showDataInUI(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}