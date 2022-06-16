package com.saboon.myprograms.Fragments.ManageProgram

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.FROM_DETAILS_PROGRAM
import com.saboon.myprograms.Utils.ShowAlertDialog
import com.saboon.myprograms.ViewModels.ManageProgramVM.DetailsProgramViewModel
import com.saboon.myprograms.databinding.FragmentDetailsProgramBinding


class DetailsProgramFragment : Fragment() {

    private var _binding: FragmentDetailsProgramBinding?=null
    private val binding get() = _binding!!


    lateinit var program: ModelProgram

    lateinit var viewModel: DetailsProgramViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_details_program, container, false)
        _binding = FragmentDetailsProgramBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailsProgramViewModel::class.java)


        arguments.let {
            if (it != null) {
                val programID = it.getString("programID").toString()
                viewModel.getProgram(programID)
            }
        }



        observer()
        buttons()
    }

    private fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                program = it
                binding.textViewDetailsGoToBack.text = program.name
                binding.textViewProgramDateAdded.text = program.dateCreated
                binding.textViewProgramDateEdited.text = program.dateEdited
            }
        })
    }

    private fun buttons(){
        binding.textViewDetailsGoToBack.setOnClickListener {
            val action = DetailsProgramFragmentDirections.actionDetailsProgramFragmentToManageProgramsFragment()
            it.findNavController().navigate(action)
        }

        binding.edit.setOnClickListener {
            val action = DetailsProgramFragmentDirections.actionDetailsProgramFragmentToAddEditProgramFragment(FROM_DETAILS_PROGRAM, program.id)
            it.findNavController().navigate(action)
        }

        binding.delete.setOnClickListener {view->
            ShowAlertDialog(requireActivity(),requireContext()).showDeleteAlertDialog("Delete","Are you sure to delete?"){
                if (it){
                    viewModel.deleteProgram(program.id)
                    val action = DetailsProgramFragmentDirections.actionDetailsProgramFragmentToManageProgramsFragment()
                    view.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}