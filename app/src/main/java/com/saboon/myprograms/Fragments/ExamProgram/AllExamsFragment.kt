package com.saboon.myprograms.Fragments.ExamProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentAllExamsBinding
import com.saboon.myprograms.databinding.FragmentSubjectsBinding

class AllExamsFragment : Fragment() {

    private var _binding: FragmentAllExamsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_all_exams, container, false)
        _binding = FragmentAllExamsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}