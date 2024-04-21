package com.ubayadev.informaticsengineeringubaya.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.informaticsengineeringubaya.databinding.FragmentHomeBinding
import com.ubayadev.informaticsengineeringubaya.viewmodel.HomeViewModel


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val programListAdapter  = ProgramAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            viewModel.refresh()
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = programListAdapter
        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.txtName.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.btnProfile.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        binding.btnProfile.setOnClickListener{
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentProfile(0)
            Navigation.findNavController(it).navigate(action)
        }
    }
    private fun observeViewModel() {
        viewModel.programLiveData.observe(viewLifecycleOwner, Observer {
            programListAdapter.updateprogramList(it)
        })
        viewModel.usernameLiveData.observe(viewLifecycleOwner, Observer {
            binding.txtName.text = "Welcome, $it"
        })
        viewModel.programLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError?.visibility = View.VISIBLE
            } else {
                binding.txtError?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recView.visibility = View.GONE
                binding.btnProfile.visibility=View.GONE
                binding.txtName.visibility=View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.btnProfile.visibility=View.VISIBLE
                binding.txtName.visibility=View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
}