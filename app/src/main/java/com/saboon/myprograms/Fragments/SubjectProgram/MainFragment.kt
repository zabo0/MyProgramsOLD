package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.ManageProgram.ManageProgramsFragmentRecyclerAdapter
import com.saboon.myprograms.Adapters.SubjectProgram.MainFragmentRecyclerAdapter
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.ViewModels.SubjectVM.MainFragmentViewModel
import com.saboon.myprograms.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding ?= null
    private val binding get() = _binding!!

    lateinit var viewModel: MainFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false)
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)


        binding.subjectMainRecycler.layoutManager = LinearLayoutManager(context)

        buttons()

    }




    fun buttons(){
        binding.subjectMainImageViewGoToAllSubjects.setOnClickListener {
            //go to all subject
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}