package com.saboon.myprograms.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.saboon.myprograms.R
import com.saboon.myprograms.databinding.FragmentDetailsProgramBinding
import com.saboon.myprograms.databinding.FragmentManageProgramsBinding


class DetailsProgramFragment : Fragment() {

    private var _binding: FragmentDetailsProgramBinding?=null
    private val binding get() = _binding!!

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

        buttons()
    }

    fun buttons(){
        binding.textViewDetailsGoToBack.setOnClickListener {
            val action = DetailsProgramFragmentDirections.actionDetailsProgramFragmentToManageProgramsFragment()
            it.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}