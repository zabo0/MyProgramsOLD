package com.saboon.myprograms.Fragments.SubjectProgram

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Activities.MainActivity
import com.saboon.myprograms.Adapters.SubjectProgram.MainFragmentRecyclerAdapter
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectProgramMainSection
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.PROGRAM_DIET
import com.saboon.myprograms.Utils.PROGRAM_EXAM
import com.saboon.myprograms.Utils.PROGRAM_SUBJECT
import com.saboon.myprograms.Utils.SHARED_PREF_ID
import com.saboon.myprograms.ViewModels.SubjectVM.MainFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectMainBinding


class SubjectMainFragment : Fragment() {

    private var _binding: FragmentSubjectMainBinding?= null
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
        _binding = FragmentSubjectMainBinding.inflate(inflater,container,false)
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
                    }
                })
            }
        }

        binding.subjectMainRecycler.layoutManager = LinearLayoutManager(context)

        observer()
        buttons()
    }

    private fun observer(){
        viewModel.program.observe(viewLifecycleOwner, Observer {
            if (it != null){
                program = it
                binding.topAppBar.title = program.name
                when(program.typeOfProgram){
                    PROGRAM_SUBJECT -> {binding.topAppBar.subtitle = "Subject Program"}
                    PROGRAM_EXAM -> {binding.topAppBar.subtitle = "Exam Program"}
                    PROGRAM_DIET -> {binding.topAppBar.subtitle = "Diet Program"}
                }
            }
        })

        viewModel.subjects.observe(viewLifecycleOwner, Observer {subjects->
            if (subjects != null){
                subjectList = subjects
                viewModel.subjectTimes.observe(viewLifecycleOwner, Observer { subjectTimes->
                    if (subjectTimes!= null){
                        subjectTimeList = subjectTimes

                        binding.subjectMainRecycler.visibility = View.VISIBLE
                        binding.progressBarLoading.visibility = View.GONE
                        binding.linearLayoutEmpty.visibility = View.GONE
                        binding.linearLayoutError.visibility = View.GONE

                        binding.subjectMainRecycler.adapter = MainFragmentRecyclerAdapter(getDataToRecyclerView(subjects,subjectTimes))

                    }
                })
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.subjectMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.empty.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.subjectMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.VISIBLE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.subjectMainRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.VISIBLE
            }
        })
    }


    private fun buttons(){

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.goToAllSubject -> {
                    val actionToSubjects = SubjectMainFragmentDirections.actionMainFragmentToSubjectsFragment(program.id)
                    findNavController().navigate(actionToSubjects)
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.buttonAddNewSubject.setOnClickListener {
            val action = SubjectMainFragmentDirections.actionMainFragmentToAddEditSubjectFragment(program.id,null)
            it.findNavController().navigate(action)
        }
    }

    private fun getDataToRecyclerView(subjects:List<ModelSubject>, subjectTimes: List<ModelSubjectTime>):ArrayList<ModelSubjectProgramMainSection>{
        //burasi main recycler view de dersleri gunlere gore ayirabilmek icin var
        //main recycler viewde ic ice iki tane recycler view var biri gunlere gore ayirmak icin icteki olan ise o gunlerde olan dersleri gostermek icin
        //bu fonksiyonda yapilan su for dongusu icerisinde haftanin her bir gunune denk gelen sayilar dondurulecek
        //databaseden cekiler subjectTimelerin icerisinde bu gune esit olan (yani mesela pazartesi gununde olan) butun timeler tek bir listeye eklenerek
        //section list icerisine kaydediliyor.
        val sectionList: ArrayList<ModelSubjectProgramMainSection> = arrayListOf()
        for (day in 0..6){
            val sbjTimeSectionList: ArrayList<ModelSubjectTime> = arrayListOf()

            for(sbjTime in subjectTimes){
                if (sbjTime.day == day.toString()){
                    sbjTimeSectionList.add(sbjTime)
                }
            }

            if (sbjTimeSectionList.isNotEmpty()){
                val section = ModelSubjectProgramMainSection(day,subjects,sbjTimeSectionList)
                sectionList.add(section)
            }
        }

        return sectionList
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}