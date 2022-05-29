package com.saboon.myprograms.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.ManageProgramsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Utils.FROM_MANAGE_PROGRAMS
import com.saboon.myprograms.ViewModels.ManageProgramsViewModel
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

        observer()

        binding.recyclerViewManageProgramsFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewManageProgramsFragment.adapter = ManageProgramsFragmentRecyclerAdapter(programList)

        buttons()
    }

    fun observer(){

        viewModel.states.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it.loading){
                    binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    binding.linearLayoutError.visibility = View.GONE
                }
                else if (it.empty){
                    binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                    binding.progressBarLoading.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    binding.linearLayoutError.visibility = View.GONE
                }
                else if (it.error){
                    binding.recyclerViewManageProgramsFragment.visibility = View.GONE
                    binding.progressBarLoading.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    binding.linearLayoutError.visibility = View.VISIBLE
                }
                else{
                    viewModel.programs.observe(viewLifecycleOwner, Observer {
                        it.let {
                            programList = it!!
                            binding.recyclerViewManageProgramsFragment.visibility = View.VISIBLE
                            binding.progressBarLoading.visibility = View.GONE
                            binding.linearLayoutEmpty.visibility = View.GONE
                            binding.linearLayoutError.visibility = View.GONE
                        }
                    })
                }
            }
        })
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