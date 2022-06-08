package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.Utils.FROM_ADD_EDIT_SUBJECT_FRAGMENT
import com.saboon.myprograms.Utils.FROM_ALL_SUBJECT_FRAGMENT
import com.saboon.myprograms.Utils.FROM_MAIN_FRAGMENT
import com.saboon.myprograms.ViewModels.SubjectVM.SubjectDetailsFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding

class SubjectDetailsFragment : Fragment() {


    private var _binding: FragmentSubjectDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: SubjectDetailsFragmentViewModel

    lateinit var subject: ModelSubject
    lateinit var subjectTimeList: List<ModelSubjectTime>
    lateinit var program: ModelProgram

    lateinit var from: String


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
                val subjectID = SubjectDetailsFragmentArgs.fromBundle(it).subjectID
                viewModel.getSubject(subjectID)

                from = SubjectDetailsFragmentArgs.fromBundle(it).from

            }
        }

        binding.subjectDetailsRecycler.layoutManager = LinearLayoutManager(context)


        observer()
        buttons()
    }


    private fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subject = it
                viewModel.getSubjectTimes(subject.id)
                binding.subjectDetailsTextViewSubjectName.text = subject.subjectName
                binding.subjectDetailsTextViewLecturerName.text = subject.lecturerName
                binding.subjectDetailsTextViewAbsenteeism.text = subject.absenteeism
            }
        })

        viewModel.subjectTimes.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subjectTimeList = it
                binding.subjectDetailsRecycler.adapter = SubjectDetailsFragmentRecyclerAdapter(subjectTimeList)
                binding.subjectDetailsRecycler.addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }
        })
    }

    private fun buttons(){
        binding.subjectDetailsImageViewEdit.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectFragment(subject.belowProgram,subject.id)
            it.findNavController().navigate(action)
        }

        binding.subjectDetailsImageViewDelete.setOnClickListener { view ->
            viewModel.deleteSubjectTimes(subject.id)
            viewModel.deleteSubject(subject.id){
                if(it){
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                    view.findNavController().navigate(action)
                }
            }
        }

        binding.subjectDetailsTextViewSubjectNameGotoBack.setOnClickListener {


            when(from){
                FROM_MAIN_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToMainFragment()
                    it.findNavController().navigate(action)
                }
                FROM_ALL_SUBJECT_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                    it.findNavController().navigate(action)
                }
                FROM_ADD_EDIT_SUBJECT_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToMainFragment()
                    it.findNavController().navigate(action)
                }
            }
        }

        binding.subjectDetailsTextViewAddTime.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectTimeFragment(subject.id,null)
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}