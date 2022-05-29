package com.saboon.myprograms.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_DETAILS_PROGRAM
import com.saboon.myprograms.Utils.FROM_MANAGE_PROGRAMS
import com.saboon.myprograms.databinding.FragmentAddEditProgramBinding

class AddEditProgramFragment : Fragment() {

    private var _binding: FragmentAddEditProgramBinding?=null
    private val binding get() = _binding!!


    private lateinit var from: String


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

        arguments.let {
            if (it != null) {
                from = it.getString("from").toString()
            }
        }


        buttons()
    }


    fun buttons(){
        binding.textViewAddEditGoToBack.setOnClickListener {
            when(from){
                FROM_MANAGE_PROGRAMS -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToManageProgramsFragment()
                    it.findNavController().navigate(action)
                }

                FROM_DETAILS_PROGRAM -> {
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment()
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
                    val action = AddEditProgramFragmentDirections.actionAddEditProgramFragmentToDetailsProgramFragment()
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