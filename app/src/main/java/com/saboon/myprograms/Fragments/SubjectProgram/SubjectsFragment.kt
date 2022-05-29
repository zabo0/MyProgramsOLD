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
import com.saboon.myprograms.Adapters.SubjectProgram.MainFragmentRecyclerAdapter
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.ModelSubject
import com.saboon.myprograms.R
import com.saboon.myprograms.ViewModels.SubjectVM.MainFragmentViewModel
import com.saboon.myprograms.ViewModels.SubjectVM.SubjectsFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectsBinding

class SubjectsFragment : Fragment() {


    private var _binding: FragmentSubjectsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: SubjectsFragmentViewModel

    lateinit var subjectList: List<ModelSubject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_subjects, container, false)

        _binding = FragmentSubjectsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this).get(SubjectsFragmentViewModel::class.java)
        viewModel.getAllSubject()

        binding.subjectRecycler.layoutManager = LinearLayoutManager(context)

        observer()
    }

    fun observer(){
        viewModel.subjects.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if(it != null) {
                    subjectList = it
                    binding.subjectRecycler.visibility = View.VISIBLE
                    binding.progressBarLoading.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    binding.linearLayoutError.visibility = View.GONE
                    binding.subjectRecycler.adapter = SubjectsFragmentRecyclerAdapter(subjectList)
                    binding.subjectRecycler.addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.subjectRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.empty.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.subjectRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.VISIBLE
                binding.linearLayoutError.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.subjectRecycler.visibility = View.GONE
                binding.progressBarLoading.visibility = View.GONE
                binding.linearLayoutEmpty.visibility = View.GONE
                binding.linearLayoutError.visibility = View.VISIBLE
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}