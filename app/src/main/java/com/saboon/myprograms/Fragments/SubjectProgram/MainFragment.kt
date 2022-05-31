package com.saboon.myprograms.Fragments.SubjectProgram

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Activities.MainActivity
import com.saboon.myprograms.Models.ModelProgram
import com.saboon.myprograms.Models.ModelSharedPref
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.Models.ModelSubjectTime
import com.saboon.myprograms.Utils.SHARED_PREF_ID
import com.saboon.myprograms.ViewModels.SubjectVM.MainFragmentViewModel
import com.saboon.myprograms.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding ?= null
    private val binding get() = _binding!!

    lateinit var viewModel: MainFragmentViewModel

    lateinit var program: ModelProgram
    lateinit var subjectList: List<ModelSubject>
    lateinit var subjectTimeList: List<ModelSubjectTime>


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

        viewModel.getLastProgramID(SHARED_PREF_ID){
            if (it){
                viewModel.sharedPref.observe(viewLifecycleOwner, Observer { sharedPref ->
                    if (sharedPref != null){
                        viewModel.getData(sharedPref.lastProgramID)
                    }else{
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                })
            }
        }

        binding.subjectMainRecycler.layoutManager = LinearLayoutManager(context)


        observer()
        buttons()

    }

    fun observer(){

        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.subjectMainTextViewChooseProgram.text = it.name
            }
        })

        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            if (it != null){
                for (subject in it){
                    println(subject.subjectName)
                }
            }
        })
    }




    fun buttons(){
        binding.subjectMainImageViewGoToAllSubjects.setOnClickListener {
            //go to all subject
        }

        binding.subjectMainTextViewChooseProgram.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}