package com.saboon.myprograms.Fragments.ManageProgram

import android.app.Activity
import android.content.Intent
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
import com.saboon.myprograms.Activities.MainActivity
import com.saboon.myprograms.Activities.SubjectProgramActivity
import com.saboon.myprograms.Adapters.ManageProgram.ManageProgramsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.ManageProgramVM.ManageProgramsViewModel
import com.saboon.myprograms.databinding.FragmentManageProgramsBinding

class ManageProgramsFragment : Fragment() {

    private var _binding: FragmentManageProgramsBinding ?= null
    private val binding get() = _binding!!


    lateinit var viewModel: ManageProgramsViewModel

    lateinit var programList: List<ModelProgram>


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

        viewModel = ViewModelProvider(this).get(ManageProgramsViewModel::class.java)



        viewModel.getAllPrograms()
        binding.recyclerViewManageProgramsFragment.layoutManager = LinearLayoutManager(context)

        observer()
        buttons()
    }

    fun observer(){


        viewModel.programs.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                programList = it
                binding.recyclerViewManageProgramsFragment.visibility = View.VISIBLE
                //binding.manageEditTextSearch.visibility = View.VISIBLE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
                binding.recyclerViewManageProgramsFragment.adapter = ManageProgramsFragmentRecyclerAdapter(programList, requireActivity().application)
                binding.recyclerViewManageProgramsFragment.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                //binding.manageEditTextSearch.visibility = View.GONE
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.empty.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                //binding.manageEditTextSearch.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.VISIBLE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                //binding.manageEditTextSearch.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.VISIBLE
            }
        })
    }


    fun buttons(){
//        binding.goToSettings.setOnClickListener {
//           //go to settings
//        }

        binding.buttonAddNewProgram.setOnClickListener {
            val action = ManageProgramsFragmentDirections.actionManageProgramsFragmentToAddEditProgramFragment(FROM_MANAGE_PROGRAMS,null)
            it.findNavController().navigate(action)
        }

        binding.fab.setOnClickListener {
            val action = ManageProgramsFragmentDirections.actionManageProgramsFragmentToAddEditProgramFragment(FROM_MANAGE_PROGRAMS,null)
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}