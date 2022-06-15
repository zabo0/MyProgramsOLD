package com.saboon.myprograms.Fragments.SubjectProgram

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
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT
import com.saboon.myprograms.Utils.IDGenerator
import com.saboon.myprograms.ViewModels.SubjectVM.AddEditSubjectTimeFragmentViewModel
import com.saboon.myprograms.databinding.FragmentAddEditSubjectTimeBinding


class AddEditSubjectTimeFragment : Fragment() {


    private var _binding: FragmentAddEditSubjectTimeBinding?=null
    private val binding get() = _binding!!


    private lateinit var viewModel: AddEditSubjectTimeFragmentViewModel
    lateinit var program: ModelProgram
    lateinit var subject: ModelSubject
    lateinit var subjectTime: ModelSubjectTime


    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterReminder: ArrayAdapter<String>
    lateinit var arrayAdapterTypeOfSubject: ArrayAdapter<String>

    private var isNewSubjectTime = true


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
                        isNewSubjectTime = false
                        binding.delete.visibility = View.VISIBLE
                    }
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
        binding.autoCompleteTextViewTypeOfSubject.setAdapter(arrayAdapterTypeOfSubject)

        //baslagicta gun ve hatirlaticiya default deger verilir(pazartesi ve hatirlatici yok)
        binding.autoCompleteTextViewWhichDay.setText(arrayAdapterDays.getItem(0),false)
        binding.autoCompleteTextViewReminderPicker.setText(arrayAdapterReminder.getItem(0),false)



        binding.addEditSubjectTimeEditTextStartTimePicker.setOnClickListener {
            getTime {
                binding.addEditSubjectTimeEditTextStartTimePicker.setText(it)
            }
        }

        binding.addEditSubjectTimeEditTextFinishTimePicker.setOnClickListener {
            getTime {
                binding.addEditSubjectTimeEditTextFinishTimePicker.setText(it)
            }
        }




        observer()
        buttons()
    }


    private fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                program = it
            }
        })

        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if (it != null){
                subject = it
                viewModel.getProgram(subject.belowProgram)
                binding.addEditSubjectTimeTextViewGoToBack.text = subject.subjectName
            }
        })

        viewModel.subjectTime.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subjectTime = it
                showDataInUI()
            }
        })
    }

    private fun buttons(){
        binding.buttonSave.setOnClickListener {view->

            if (isNewSubjectTime){
                viewModel.saveSubjectTime(newSubjectTime()){
                    if (it){
                        val action = AddEditSubjectTimeFragmentDirections.actionAddEditSubjectTimeFragmentToSubjectDetailsFragment(subject.id, FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT)
                        view.findNavController().navigate(action)
                    }
                }
            }else{
                updateSubjectTime()
                viewModel.updateSubjectTime(subjectTime){
                    val action = AddEditSubjectTimeFragmentDirections.actionAddEditSubjectTimeFragmentToSubjectDetailsFragment(subject.id,FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT)
                    view.findNavController().navigate(action)
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            val action = AddEditSubjectTimeFragmentDirections.actionAddEditSubjectTimeFragmentToSubjectDetailsFragment(subject.id,FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT)
            it.findNavController().navigate(action)
        }

        binding.addEditSubjectTimeTextViewGoToBack.setOnClickListener {
            val action = AddEditSubjectTimeFragmentDirections.actionAddEditSubjectTimeFragmentToSubjectDetailsFragment(subject.id,FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT)
            it.findNavController().navigate(action)
        }

        binding.delete.setOnClickListener { view ->
            viewModel.deleteSubjectTime(subjectTime.id){
                val action = AddEditSubjectTimeFragmentDirections.actionAddEditSubjectTimeFragmentToSubjectDetailsFragment(subject.id,FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT)
                view.findNavController().navigate(action)
            }
        }
    }

    private fun newSubjectTime(): ModelSubjectTime {
        val dayInt = requireActivity().resources.getStringArray(R.array.Days).indexOf(binding.autoCompleteTextViewWhichDay.text.toString()).toString()
        val dayString = binding.autoCompleteTextViewWhichDay.text.toString() //id icin
        val classroom = binding.addEditSubjectTimeEditTextClassroom.text.toString()
        val startTime = binding.addEditSubjectTimeEditTextStartTimePicker.text.toString()
        val finishTime = binding.addEditSubjectTimeEditTextFinishTimePicker.text.toString()
        val typeOfSubject = binding.autoCompleteTextViewTypeOfSubject.text.toString()
        val reminderTime = requireActivity().resources.getStringArray(R.array.reminder).indexOf(binding.autoCompleteTextViewReminderPicker.text.toString()).toString()

        val day_timeStart = "${dayString}_${startTime}"//id icin
        val id = IDGenerator().generateTimeID(program.name,subject.subjectName!!,day_timeStart)
        val notificationID = IDGenerator().generateNotificationID(id)

        val newSubject = ModelSubjectTime(id,dayInt,startTime,finishTime,typeOfSubject,classroom,reminderTime,notificationID,subject.id,subject.belowProgram)

        return newSubject
    }

    private fun updateSubjectTime(){
        subjectTime.day = requireActivity().resources.getStringArray(R.array.Days).indexOf(binding.autoCompleteTextViewWhichDay.text.toString()).toString()
        subjectTime.classRoom = binding.addEditSubjectTimeEditTextClassroom.text.toString()
        subjectTime.timeStart = binding.addEditSubjectTimeEditTextStartTimePicker.text.toString()
        subjectTime.timeFinish = binding.addEditSubjectTimeEditTextFinishTimePicker.text.toString()
        subjectTime.typeOfLesson = binding.autoCompleteTextViewTypeOfSubject.text.toString()
        subjectTime.reminderTime = requireActivity().resources.getStringArray(R.array.reminder).indexOf(binding.autoCompleteTextViewReminderPicker.text.toString()).toString()
    }

    private fun showDataInUI(){
        binding.autoCompleteTextViewWhichDay.setText(arrayAdapterDays.getItem(subjectTime.day!!.toInt()), false)
//        binding.autoCompleteTextViewWhichDay.setText(subjectTime.day)
        binding.addEditSubjectTimeEditTextClassroom.setText(subjectTime.classRoom)
        binding.addEditSubjectTimeEditTextStartTimePicker.setText(subjectTime.timeStart)
        binding.addEditSubjectTimeEditTextFinishTimePicker.setText(subjectTime.timeFinish)
        binding.autoCompleteTextViewTypeOfSubject.setText(subjectTime.typeOfLesson)
        binding.autoCompleteTextViewReminderPicker.setText(arrayAdapterReminder.getItem(subjectTime.reminderTime!!.toInt()), false)
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