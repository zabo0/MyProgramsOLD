package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
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
    private var subjectColor = SUBJECT_COLOR_RED

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
                }else{
                    binding.radioRed.isChecked = true
                }
            }
        }
        binding.colorPickerRadioGroupFirst.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_red -> {
                    binding.colorPickerRadioGroupSecond.clearCheck()
                    subjectColor = SUBJECT_COLOR_RED
                }
                R.id.radio_yellow -> {
                    binding.colorPickerRadioGroupSecond.clearCheck()
                    subjectColor = SUBJECT_COLOR_YELLOW
                }
                R.id.radio_blue -> {
                    binding.colorPickerRadioGroupSecond.clearCheck()
                    subjectColor = SUBJECT_COLOR_BLUE
                }
            }
        }

        binding.colorPickerRadioGroupSecond.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_green ->{
                    binding.colorPickerRadioGroupFirst.clearCheck()
                    subjectColor = SUBJECT_COLOR_GREEN
                }
                R.id.radio_orange -> {
                    binding.colorPickerRadioGroupFirst.clearCheck()
                    subjectColor = SUBJECT_COLOR_ORANGE
                }
                R.id.radio_pink -> {
                    binding.colorPickerRadioGroupFirst.clearCheck()
                    subjectColor = SUBJECT_COLOR_PINK
                }
            }
        }



        observer()
        buttons()
    }


    private fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if( it!= null){
                subject = it
                binding.subjectAddEditSubjectEditTextSubjectName.setText(it.subjectName)
                binding.subjectAddEditSubjectEditTextLecturerName.setText(it.lecturerName)
                binding.subjectAddEditSubjectEditTextMaxAbsenteeism.setText(it.absenteeism)

                when(subject.color){
                    SUBJECT_COLOR_RED->{
                        binding.radioRed.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    SUBJECT_COLOR_YELLOW->{
                        binding.radioYellow.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    SUBJECT_COLOR_BLUE->{
                        binding.radioBlue.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    SUBJECT_COLOR_GREEN->{
                        binding.radioGreen.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                    SUBJECT_COLOR_ORANGE->{
                        binding.radioOrange.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                    SUBJECT_COLOR_PINK->{
                        binding.radioPink.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                }
            }
        })

        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                program = it
            }
        })


    }

    private fun buttons(){
        binding.buttonSave.setOnClickListener { view ->
           if (isNewSubject){
               val newSubject = newSubject()
               viewModel.saveNewSubject(newSubject){
                   val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(newSubject.id, FROM_ADD_EDIT_SUBJECT_FRAGMENT)
                   view.findNavController().navigate(action)
               }
           }else{
               subject.subjectName = binding.subjectAddEditSubjectEditTextSubjectName.text.toString().trimEnd()
               subject.lecturerName = binding.subjectAddEditSubjectEditTextLecturerName.text.toString().trimEnd()
               subject.absenteeism = binding.subjectAddEditSubjectEditTextMaxAbsenteeism.text.toString().trimEnd()
               subject.dateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
               subject.color = subjectColor

               viewModel.updateSubject(subject){
                   val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(subject.id,
                       FROM_ADD_EDIT_SUBJECT_FRAGMENT)
                   view.findNavController().navigate(action)
               }
           }
        }

        binding.textViewAddEditSubjectGoToBack.setOnClickListener {
            if (isNewSubject){
                val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectsFragment(program.id)
                it.findNavController().navigate(action)
            }else{
                val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(subject.id, FROM_ADD_EDIT_SUBJECT_FRAGMENT)
                it.findNavController().navigate(action)
            }
        }

        binding.buttonCancel.setOnClickListener {
            if (isNewSubject){
                val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectsFragment(program.id)
                it.findNavController().navigate(action)
            }else{
                val action = AddEditSubjectFragmentDirections.actionAddEditSubjectFragmentToSubjectDetailsFragment(subject.id, FROM_ADD_EDIT_SUBJECT_FRAGMENT)
                it.findNavController().navigate(action)
            }
        }
    }

    private fun newSubject():ModelSubject{
        val subjectName = binding.subjectAddEditSubjectEditTextSubjectName.text.toString().trimEnd()
        val lecturerName = binding.subjectAddEditSubjectEditTextLecturerName.text.toString().trimEnd()
        val color = subjectColor
        val maxAbsenteeism = binding.subjectAddEditSubjectEditTextMaxAbsenteeism.text.toString().trimEnd()
        val dateAdded = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
        val dateEdited = dateAdded
        val belowProgID = program.id
        val id = IDGenerator().generateSubjectID(program.name,subjectName)

        return ModelSubject(id,dateAdded,dateEdited,subjectName,lecturerName,color, maxAbsenteeism,belowProgID)
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_red ->
                    if (checked) {
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        subjectColor = SUBJECT_COLOR_RED
                    }
                R.id.radio_yellow ->
                    if (checked) {
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        subjectColor = SUBJECT_COLOR_YELLOW
                    }
                R.id.radio_blue ->
                    if (checked) {
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        subjectColor = SUBJECT_COLOR_BLUE
                    }
                R.id.radio_green ->
                    if (checked) {
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        subjectColor = SUBJECT_COLOR_GREEN
                    }
                R.id.radio_orange ->
                    if (checked) {
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        subjectColor = SUBJECT_COLOR_ORANGE
                    }
                R.id.radio_pink ->
                    if (checked) {
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        subjectColor = SUBJECT_COLOR_PINK
                    }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}