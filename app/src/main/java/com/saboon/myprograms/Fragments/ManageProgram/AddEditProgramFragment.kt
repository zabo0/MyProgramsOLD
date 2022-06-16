package com.saboon.myprograms.Fragments.ManageProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.ManageProgramVM.AddEditProgramViewModel
import com.saboon.myprograms.databinding.FragmentAddEditProgramBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditProgramFragment : Fragment() {

    private var _binding: FragmentAddEditProgramBinding?=null
    private val binding get() = _binding!!


    private lateinit var from: String

    lateinit var program: ModelProgram

    private lateinit var viewModel: AddEditProgramViewModel
    private var typeOfProgram = PROGRAM_SUBJECT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_edit_program, container, false)
        _binding = FragmentAddEditProgramBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(AddEditProgramViewModel::class.java)

        arguments.let {
            if (it != null) {
                from = AddEditProgramFragmentArgs.fromBundle(it).from
                val programID = AddEditProgramFragmentArgs.fromBundle(it).programID
                if (programID != null) {
                    viewModel.getProgram(programID)
                    binding.topAppBar.title = resources.getString(R.string.editProgram)
                    binding.radioSubjectProgram.isEnabled = false
                    binding.radioExamProgram.isEnabled = false
                    binding.radioDietProgram.isEnabled = false
                }
            }
        }

        binding.radioGroupTypeOfProgram.setOnCheckedChangeListener { radioGroup, checkedID ->
            // Check which radio button was clicked
            when (checkedID) {
                R.id.radio_subjectProgram ->{
                    typeOfProgram = PROGRAM_SUBJECT
                }
                R.id.radio_examProgram ->{
                    typeOfProgram = PROGRAM_EXAM
                }
                R.id.radio_dietProgram ->{
                    typeOfProgram = PROGRAM_DIET
                }
            }
        }




        observer()
        buttons()
    }

    private fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                program = it
                binding.editTextProgramName.setText(program.name)
                when(program.typeOfProgram){
                    PROGRAM_SUBJECT -> {
                        binding.radioSubjectProgram.isChecked = true
                    }
                    PROGRAM_EXAM -> {
                        binding.radioExamProgram.isChecked = true
                    }
                    PROGRAM_DIET -> {
                        binding.radioDietProgram.isChecked = true
                    }
                }
            }
        })
    }


    private fun buttons(){

        binding.topAppBar.setNavigationOnClickListener {
            when(from){
                FROM_MANAGE_PROGRAMS -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToManageProgramsFragment()
                    it.findNavController().navigate(action)
                }

                FROM_DETAILS_PROGRAM -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment(program.id)
                    it.findNavController().navigate(action)
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            when(from){
                FROM_MANAGE_PROGRAMS -> {
                    val name = binding.editTextProgramName.text.toString().trimEnd()
                    val id = IDGenerator().generateProgramID(name)
                    val dateCreated = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
                    val dateEdited = dateCreated

                    val program = ModelProgram(id,name,dateCreated,dateEdited, typeOfProgram)

                    viewModel.storeProgram(program){
                        val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToManageProgramsFragment()
                        findNavController().navigate(action)
                    }
                }

                FROM_DETAILS_PROGRAM -> {
                    val newName = binding.editTextProgramName.text.toString().trimEnd()
                    val newDateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)

                    viewModel.updateProgram(program.id, newName, newDateEdited){
                        val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment(program.id)
                        findNavController().navigate(action)
                    }
                }
            }
        }


        binding.buttonCancel.setOnClickListener {
            when(from){
                FROM_MANAGE_PROGRAMS -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToManageProgramsFragment()
                    it.findNavController().navigate(action)
                }

                FROM_DETAILS_PROGRAM -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment(program.id)
                    it.findNavController().navigate(action)
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}