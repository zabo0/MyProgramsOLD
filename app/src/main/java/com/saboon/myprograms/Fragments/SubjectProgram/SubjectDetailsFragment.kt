package com.saboon.myprograms.Fragments.SubjectProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding

class SubjectDetailsFragment : Fragment() {


    private var _binding: FragmentSubjectDetailsBinding?=null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_subject_details, container, false)
        _binding = FragmentSubjectDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}