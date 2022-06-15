package com.saboon.myprograms.Fragments.SubjectProgram

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saboon.myprograms.Adapters.SubjectProgram.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.Models.Program.ModelProgram
import com.saboon.myprograms.Models.Subject.ModelSubject
import com.saboon.myprograms.Models.Subject.ModelSubjectTime
import com.saboon.myprograms.Utils.FROM_ADD_EDIT_SUBJECT_FRAGMENT
import com.saboon.myprograms.Utils.FROM_ADD_EDIT_SUBJECT_TIME_FRAGMENT
import com.saboon.myprograms.Utils.FROM_ALL_SUBJECT_FRAGMENT
import com.saboon.myprograms.Utils.FROM_MAIN_FRAGMENT
import com.saboon.myprograms.ViewModels.SubjectVM.SubjectDetailsFragmentViewModel
import com.saboon.myprograms.databinding.FragmentSubjectDetailsBinding


class SubjectDetailsFragment : Fragment() {


    private var _binding: FragmentSubjectDetailsBinding?=null
    private val binding get() = _binding!!

    lateinit var viewModel: SubjectDetailsFragmentViewModel

    lateinit var subject: ModelSubject
    lateinit var subjectTimeList: List<ModelSubjectTime>
    lateinit var program: ModelProgram

    lateinit var from: String

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
                binding.subjectDetailsTextViewAbsenteeism.text = subject.absenteeism
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
        binding.subjectDetailsImageViewEdit.setOnClickListener {
            val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToAddEditSubjectFragment(subject.belowProgram,subject.id)
            it.findNavController().navigate(action)
        }

        binding.subjectDetailsImageViewDelete.setOnClickListener { view ->
            viewModel.deleteSubjectTimes(subject.id)
            viewModel.deleteSubject(subject.id){
                if(it){
                    val action = SubjectDetailsFragmentDirections.actionSubjectDetailsFragmentToSubjectsFragment(subject.belowProgram)
                    view.findNavController().navigate(action)
                }
            }
        }

        binding.subjectDetailsTextViewSubjectNameGotoBack.setOnClickListener {


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
            Configuration.UI_MODE_NIGHT_YES -> {}
            Configuration.UI_MODE_NIGHT_NO -> {
//                binding.linearLayout.setBackgroundColor(Color.parseColor(color))
//                binding.view.setBackgroundColor(Color.parseColor(color))
//                binding.subjectDetailsTextViewAddTime.setTextColor(Color.parseColor(color))
//                binding.subjectDetailsTextViewAbsenteeism.setTextColor(Color.parseColor(color))
//                binding.textAbsenteeism.setTextColor(Color.parseColor(color))
//                binding.subjectDetailsTextViewSubjectName.setBackgroundColor(Color.parseColor(color))
//
//
//
//                window.clearFlags(WindowManager.LayoutParams.FLAGS_CHANGED)
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = Color.parseColor(color)


//                binding.subjectDetailsTextViewSubjectName.compoundDrawables[0].setTint(Color.parseColor(color))
//                binding.subjectDetailsTextViewLecturerName.compoundDrawables[0].setTint(Color.parseColor(color))
//                binding.subjectDetailsTextViewAddTime.compoundDrawables[1].setTint(Color.parseColor(color))

                for (drawable in binding.subjectDetailsTextViewSubjectName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewLecturerName.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
                for (drawable in binding.subjectDetailsTextViewAddTime.compoundDrawables) {
                    drawable?.setTint(Color.parseColor(color))
                }
//                binding.subjectDetailsTextViewAbsenteeism.setTextColor(Color.parseColor(color))
//                binding.textAbsenteeism.setTextColor(Color.parseColor(color))
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