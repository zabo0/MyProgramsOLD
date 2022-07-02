package com.saboon.myprograms.Fragments.SubjectProgram

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.R
import com.saboon.myprograms.Utils.*
import com.saboon.myprograms.ViewModels.SubjectVM.SubjectDetailsFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding


class SubjectDetailsFragment : Fragment() {


    private var _binding: FragmentSubjectDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: SubjectDetailsFragmentViewModel

    lateinit var subject: ModelSubject
    lateinit var subjectTimeList: List<ModelSubjectTime>
    lateinit var program: ModelProgram

    private var from = 0

    lateinit var window: Window


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SubjectDetailsFragmentViewModel::class.java)
        window = requireActivity().window

        arguments.let {
            if (it!=null){
                val subjectID = SubjectDetailsFragmentArgs.fromBundle(it).subjectID
                viewModel.getSubject(subjectID)

                from = SubjectDetailsFragmentArgs.fromBundle(it).from

            }
        }

        binding.subjectDetailsRecycler.layoutManager = LinearLayoutManager(context)


        observer()
        buttons()
    }


    private fun observer(){
        viewModel.subject.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subject = it
                viewModel.getSubjectTimes(subject.id)
                makeTheme(subject.color)
                binding.subjectDetailsTextViewSubjectName.text = subject.subjectName
                binding.subjectDetailsTextViewLecturerName.text = subject.lecturerName
                val absenteeism = subject.absenteeism
                if (absenteeism == "-1"){
                    binding.subjectDetailsTextViewAbsenteeism.visibility = View.GONE
                    binding.textAbsenteeism.visibility = View.GONE
                }else{
                    binding.subjectDetailsTextViewAbsenteeism.visibility = View.VISIBLE
                    binding.textAbsenteeism.visibility = View.VISIBLE
                    binding.subjectDetailsTextViewAbsenteeism.text = subject.absenteeism
                }
            }
        })

        viewModel.subjectTimes.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                subjectTimeList = it
                binding.subjectDetailsRecycler.adapter = SubjectDetailsFragmentRecyclerAdapter(subjectTimeList, subject.color)
                binding.subjectDetailsRecycler.addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }
        })
    }

    private fun buttons(){


        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.editSubject->{
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectFragment(subject.belowProgram,subject.id)
                    findNavController().navigate(action)
                    true
                }
                R.id.deleteSubject->{
                    ShowAlertDialog(requireActivity(),requireContext()).showDeleteAlert("Delete","Are you sure to delete?"){
                        if (it){
                            viewModel.deleteSubjectTimes(subject.id)
                            viewModel.deleteSubject(subject.id){
                                if(it){
                                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                                    findNavController().navigate(action)
                                }
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            when(from){
                FROM_MAIN_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToMainFragment()
                    it.findNavController().navigate(action)
                }
                FROM_ALL_SUBJECT_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                    it.findNavController().navigate(action)
                }
                FROM_ADD_EDIT_SUBJECT_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToMainFragment()
                    it.findNavController().navigate(action)
                }

                FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT -> {
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToMainFragment()
                    it.findNavController().navigate(action)
                }
            }
        }

        binding.subjectDetailsTextViewAddTime.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectTimeFragment(subject.id,null)
            it.findNavController().navigate(action)
        }
    }

    private fun makeTheme(color: String){
        when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                for (drawable in binding.subjectDetailsTextViewSubjectName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewLecturerName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewAddTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                binding.view.setBackgroundColor(Color.parseColor(color))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                for (drawable in binding.subjectDetailsTextViewSubjectName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewLecturerName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewAddTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                binding.view.setBackgroundColor(Color.parseColor(color))
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}