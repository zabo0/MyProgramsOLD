package com.saboon.myprograms.Fragments.ExamProgram

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.saboon.myprograms.Fragments.SubjectProgram.AddEditSubjectFragmentDirections
import com.saboon.myprograms.Models.Exam.ModelExam
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.ExamVM.AddEditExamViewModel
import com.saboon.myprograms.databinding.FragmentAddEditExamBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditExamFragment : Fragment() {

    var _binding : FragmentAddEditExamBinding?= null
    val binding get() = _binding!!

    lateinit var viewModel: AddEditExamViewModel

    lateinit var exam : ModelExam
    lateinit var program : ModelProgram
    private var examColor = COLOR_SOFT_RED

    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterReminder: ArrayAdapter<String>
    lateinit var arrayAdapterTypeOfSubject: ArrayAdapter<String>

    var isNewExam = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditExamViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_edit_exam, container, false)
        _binding = FragmentAddEditExamBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments.let {
            if (it!=null){
                val programID = AddEditExamFragmentArgs.fromBundle(it).programID
                viewModel.getProgram(programID)
                val subjectID = AddEditExamFragmentArgs.fromBundle(it).examID
                if (subjectID != null){
                    isNewExam = false
                    viewModel.getExam(subjectID)
                }else{
                    binding.radioRed.isChecked = true
                }
            }
        }

        val dayItems = resources.getStringArray(R.array.Days)
        arrayAdapterDays = ArrayAdapter(requireContext(), R.layout.drop_down_list_item,dayItems)
        binding.autoCompleteTextViewWhichDay.setAdapter(arrayAdapterDays)

        val reminderItems = resources.getStringArray(R.array.reminder)
        arrayAdapterReminder = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, reminderItems)
        binding.autoCompleteTextViewReminderPicker.setAdapter(arrayAdapterReminder)

        val typeOfSubjectItems = resources.getStringArray(R.array.typeOfSubject)
        arrayAdapterTypeOfSubject = ArrayAdapter(requireContext(), R.layout.drop_down_list_item, typeOfSubjectItems)
        binding.autoCompleteTextViewTypeOfExam.setAdapter(arrayAdapterTypeOfSubject)

        //baslagicta gun ve hatirlaticiya default deger verilir(pazartesi ve hatirlatici yok)
        binding.autoCompleteTextViewWhichDay.setText(arrayAdapterDays.getItem(0),false)
        binding.autoCompleteTextViewReminderPicker.setText(arrayAdapterReminder.getItem(0),false)


        binding.colorPickerRadioGroupFirst.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_red -> {
                    if (binding.radioRed.isChecked){
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        examColor = COLOR_SOFT_RED
                    }
                }
                R.id.radio_yellow -> {
                    if (binding.radioYellow.isChecked){
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        examColor = COLOR_YELLOW
                    }
                }
                R.id.radio_blue -> {
                    if (binding.radioBlue.isChecked){
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        examColor = COLOR_BLUE
                    }
                }
                R.id.radio_brown -> {
                    if (binding.radioBrown.isChecked){
                        binding.colorPickerRadioGroupSecond.clearCheck()
                        examColor = COLOR_BROWN
                    }
                }
            }
        }

        binding.colorPickerRadioGroupSecond.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_green ->{
                    if (binding.radioGreen.isChecked){
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        examColor = COLOR_GREEN
                    }
                }
                R.id.radio_orange -> {
                    if (binding.radioOrange.isChecked){
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        examColor = COLOR_SOFT_ORANGE
                    }
                }
                R.id.radio_pink -> {
                    if (binding.radioPink.isChecked){
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        examColor = COLOR_PINK
                    }
                }

                R.id.radio_purple -> {
                    if (binding.radioPurple.isChecked){
                        binding.colorPickerRadioGroupFirst.clearCheck()
                        examColor = COLOR_PURPLE
                    }
                }
            }
        }

        binding.examAddEditExamEditTextStartTimePicker.setOnClickListener {
            getTime {
                binding.examAddEditExamEditTextStartTimePicker.setText(it)
            }
        }

        binding.examAddEditExamEditTextFinishTimePicker.setOnClickListener {
            getTime {
                binding.examAddEditExamEditTextFinishTimePicker.setText(it)
            }
        }

        observer()
        buttons()

    }

    private fun buttons() {
        binding.buttonSave.setOnClickListener {view->
            if (isNewExam){
                val newExam = newExam()
                viewModel.saveNewExam(newExam){
                    val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToExamDetailsFragment(newExam.id,
                        FROM_ADD_EDIT_EXAM_FRAGMENT)
                    view.findNavController().navigate(action)
                }
            }else{

                updateExam()
                viewModel.updateExam(exam){
                    val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToExamDetailsFragment(exam.id, FROM_ADD_EDIT_EXAM_FRAGMENT)
                    view.findNavController().navigate(action)
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            if (isNewExam){
                val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToAllExamsFragment(program.id)
                it.findNavController().navigate(action)
            }else{
                val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToExamDetailsFragment(exam.id, FROM_ADD_EDIT_EXAM_FRAGMENT)
                it.findNavController().navigate(action)
            }
        }

        binding.isExamDone.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                binding.inputLayoutPoint.visibility = View.VISIBLE
            }else{
                binding.inputLayoutPoint.visibility = View.GONE
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            if (isNewExam){
                val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToAllExamsFragment(program.id)
                it.findNavController().navigate(action)
            }else{
                val action = AddEditExamFragmentDirections.actionAddEditExamFragmentToExamDetailsFragment(exam.id, FROM_ADD_EDIT_EXAM_FRAGMENT)
                it.findNavController().navigate(action)
            }
        }
    }

    private fun newExam(): ModelExam {
        val examName = binding.examAddEditExamEditTextExamName.text.toString().trimEnd()
        val color = examColor
        val day = requireActivity().resources.getStringArray(R.array.Days).indexOf(binding.autoCompleteTextViewWhichDay.text.toString()).toString()
        val classroom = binding.examAddEditExamEditTextClassroom.text.toString().trimEnd()
        val startTime = binding.examAddEditExamEditTextStartTimePicker.text.toString().trimEnd()
        val finisTime = binding.examAddEditExamEditTextFinishTimePicker.text.toString().trimEnd()
        val typeOfExam = binding.autoCompleteTextViewTypeOfExam.text.toString().trimEnd()
        val targetPoint = binding.examAddEditExamEditTextTargetPoint.text.toString().trimEnd()
        var point = "-1"
        if(binding.isExamDone.isChecked){
            point = binding.examAddEditExamEditTextPoint.text.toString().trimEnd()
        }
        val reminder = requireActivity().resources.getStringArray(R.array.reminder).indexOf(binding.autoCompleteTextViewReminderPicker.text.toString()).toString()
        val isDone = binding.isExamDone.isChecked

        val dateAdded = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
        val dateEdited = dateAdded
        val belowProgID = program.id
        val id = IDGenerator().generateExamID(program.name,examName)
        val notificationID = IDGenerator().generateNotificationID(id)

        return ModelExam(id,dateAdded,dateEdited,examName,color,day,startTime,finisTime,typeOfExam,classroom,targetPoint,point,reminder,isDone,notificationID,belowProgID)
    }

    private fun updateExam(){
        exam.examName = binding.examAddEditExamEditTextExamName.text.toString().trimEnd()
        exam.color = examColor
        exam.day = requireActivity().resources.getStringArray(R.array.Days).indexOf(binding.autoCompleteTextViewWhichDay.text.toString()).toString()
        exam.classroom = binding.examAddEditExamEditTextClassroom.text.toString().trimEnd()
        exam.timeStart = binding.examAddEditExamEditTextStartTimePicker.text.toString().trimEnd()
        exam.timeFinish = binding.examAddEditExamEditTextFinishTimePicker.text.toString().trimEnd()
        exam.typeOfExam = binding.autoCompleteTextViewTypeOfExam.text.toString().trimEnd()
        exam.targetPoint = binding.examAddEditExamEditTextTargetPoint.text.toString().trimEnd()
        if(binding.isExamDone.isChecked){
            exam.isDone = true
            exam.point = binding.examAddEditExamEditTextPoint.text.toString().trimEnd()
        }else{
            exam.isDone = false
            exam.point = "-1"
        }
        exam.reminderTime = requireActivity().resources.getStringArray(R.array.reminder).indexOf(binding.autoCompleteTextViewReminderPicker.text.toString()).toString()
        exam.dateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
    }


    private fun observer(){
        viewModel.exam.observe(viewLifecycleOwner, Observer {
            if( it!= null){
                exam = it
                binding.examAddEditExamEditTextExamName.setText(exam.examName)
                binding.autoCompleteTextViewWhichDay.setText(arrayAdapterDays.getItem(exam.day.toInt()), false)
                binding.examAddEditExamEditTextClassroom.setText(exam.classroom)
                binding.examAddEditExamEditTextStartTimePicker.setText(exam.timeStart)
                binding.examAddEditExamEditTextFinishTimePicker.setText(exam.timeFinish)
                binding.autoCompleteTextViewTypeOfExam.setText(exam.typeOfExam)
                binding.examAddEditExamEditTextTargetPoint.setText(exam.targetPoint)
                binding.autoCompleteTextViewReminderPicker.setText(arrayAdapterReminder.getItem(exam.reminderTime.toInt()), false)
                if (exam.isDone){
                    binding.inputLayoutPoint.visibility = View.VISIBLE
                    binding.isExamDone.isChecked = true
                    binding.examAddEditExamEditTextPoint.setText(exam.point)
                }
                when(exam.color){
                    COLOR_SOFT_RED->{
                        binding.radioRed.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    COLOR_YELLOW->{
                        binding.radioYellow.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    COLOR_BLUE->{
                        binding.radioBlue.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    COLOR_BROWN->{
                        binding.radioBrown.isChecked = true
                        binding.colorPickerRadioGroupSecond.clearCheck()
                    }
                    COLOR_GREEN->{
                        binding.radioGreen.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                    COLOR_SOFT_ORANGE->{
                        binding.radioOrange.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                    COLOR_PINK->{
                        binding.radioPink.isChecked = true
                        binding.colorPickerRadioGroupFirst.clearCheck()
                    }
                    COLOR_PURPLE->{
                        binding.radioPurple.isChecked = true
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

    private fun getTime(callback:(String)->Unit){
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        var timeText = ""

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(0)
            .setMinute(0)
            .setTitleText("Start Time")
            .build()
        picker.show(childFragmentManager, "TAG")

        picker.addOnPositiveButtonClickListener{
            val hour = picker.hour
            val min = picker.minute
            timeText = String.format("%02d:%02d", hour, min)
            callback(timeText)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}