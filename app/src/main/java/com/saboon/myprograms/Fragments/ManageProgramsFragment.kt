package com.saboon.myprograms.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.saboon.myprograms.Utils.FROM_MANAGE_PROGRAMS
import com.saboon.myprograms.databinding.FragmentManageProgramsBinding

class ManageProgramsFragment : Fragment() {

    private var _binding: FragmentManageProgramsBinding ?= null
    private val binding get() = _binding!!




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_manage_programs, container, false)
        _binding = FragmentManageProgramsBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        buttons()
    }


    fun buttons(){
        binding.goToSettings.setOnClickListener {
            val action = ManageProgramsFragmentDirections.actionManageProgramsFragmentToDetailsProgramFragment()
            it.findNavController().navigate(action)
        }

        binding.fab.setOnClickListener {
            val action = ManageProgramsFragmentDirections.actionManageProgramsFragmentToAddEditProgramFragment(FROM_MANAGE_PROGRAMS)
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}