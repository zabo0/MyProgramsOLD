package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Utils.IDGenerator
import com.saboon.myprograms.ViewModels.SubjectVM.AddEditSubjectFragmentViewModel
import com.saboon.myprograms.databinding.FragmentAddEditSubjectBinding
import java.text.SimpleDateFormat
import java.util.*


class AddEditSubjectFragment : Fragment() {


    private var _binding: FragmentAddEditSubjectBinding ?= null
    private val binding get() = _binding!!

    lateinit var viewModel: AddEditSubjectFragmentViewModel

    lateinit var subject : ModelSubject
    lateinit var program : ModelProgram

    var isNewSubject = true

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

        arguments.let {
            if (it!=null){
                val programID = it.getString("programID")!!
                viewModel.getProgram(programID)
                val subjectID = it.getString("subjectID")
                if (subjectID != null){
                    isNewSubject = false
                    viewModel.getSubject(subjectID)
                }
            }
        }



        observer()
        buttons()
    }


    fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if( it!= null){
                subject = it
                binding.subjectAddEditSubjectEditTextSubjectName.setText(it.subjectName)
                binding.subjectAddEditSubjectEditTextLecturerName.setText(it.lecturerName)
                binding.subjectAddEditSubjectEditTextMaxAbsenteeism.setText(it.absenteeism)
            }
        })

        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                program = it
            }
        })


    }

    fun buttons(){
        binding.buttonSave.setOnClickListener { view ->
           if (isNewSubject){
               val newSubject = newSubject()
               viewModel.saveNewSubject(newSubject){
                   val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(newSubject.id)
                   view.findNavController().navigate(action)
               }
           }else{
                viewModel.updateSubject(subject){
                    val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(subject.id)
                    view.findNavController().navigate(action)
                }
           }
        }
    }


    fun newSubject():ModelSubject{
        val subjectName = binding.subjectAddEditSubjectEditTextSubjectName.text.toString().trimEnd()
        val lecturerName = binding.subjectAddEditSubjectEditTextLecturerName.text.toString().trimEnd()
        val color = "#FF0000"
        val maxAbsenteeism = binding.subjectAddEditSubjectEditTextMaxAbsenteeism.text.toString().trimEnd()
        val dateAdded = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
        val dateEdited = dateAdded
        val belowProgID = program.id
        val id = IDGenerator().generateSubjectID(program.name,subjectName)

        return ModelSubject(id,dateAdded,dateEdited,subjectName,lecturerName,color, maxAbsenteeism,belowProgID)
    }

    fun updateSubject(){
//        val subjectName = binding.subjectAddEditSubjectEditTextSubjectName.text.toString().trimEnd()
//        val lecturerName = binding.subjectAddEditSubjectEditTextLecturerName.text.toString().trimEnd()
//        val color = subject.color
//        val maxAbsenteeism = binding.subjectAddEditSubjectEditTextMaxAbsenteeism.text.toString().trimEnd()
//        val dateAdded = subject.dateAdded
//        val dateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
//        val belowProgID = subject.belowProgram
//        val id = subject.id


        subject.subjectName = binding.subjectAddEditSubjectEditTextSubjectName.text.toString().trimEnd()
        subject.lecturerName = binding.subjectAddEditSubjectEditTextLecturerName.text.toString().trimEnd()
        subject.absenteeism = binding.subjectAddEditSubjectEditTextMaxAbsenteeism.text.toString().trimEnd()
        subject.dateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)

//        return ModelSubject(id,dateAdded,dateEdited,subjectName,lecturerName,color, maxAbsenteeism,belowProgID)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}