package com.saboon.myprograms.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Utils.FROM_DETAILS_PROGRAM
import com.saboon.myprograms.Utils.FROM_MANAGE_PROGRAMS
import com.saboon.myprograms.Utils.IDGenerator
import com.saboon.myprograms.ViewModels.AddEditProgramViewModel
import com.saboon.myprograms.databinding.FragmentAddEditProgramBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditProgramFragment : Fragment() {

    private var _binding: FragmentAddEditProgramBinding?=null
    private val binding get() = _binding!!


    private lateinit var from: String

    lateinit var program: ModelProgram

    lateinit var viewModel: AddEditProgramViewModel


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
                from = it.getString("from").toString()
                val programID = it.getString("programID")
                if (programID != null) {
                    viewModel.getProgram(programID)
                }
            }
        }



        observer()
        buttons()
    }

    fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                program = it
                binding.editTextProgramName.setText(program.name)
            }
        })
    }


    fun buttons(){
        binding.textViewAddEditGoToBack.setOnClickListener {
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
                    val name = binding.editTextProgramName.text.toString()
                    val id = IDGenerator().generateProgramID(name)
                    val dateCreated = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
                    val dateEdited = SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)


                    program.id = id
                    program.name = name
                    program.dateCreated = dateCreated
                    program.dateEdited = dateEdited

                    viewModel.storeProgram(program){
                        val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment(program.id)
                        findNavController().navigate(action)
                    }
                }

                FROM_DETAILS_PROGRAM -> {
                    // TODO: set update
                    Toast.makeText(activity,"updated",Toast.LENGTH_LONG).show()
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment(program.id)
                    it.findNavController().navigate(action)
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