package com.ubayadev.informaticsengineeringubaya.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.ubayadev.informaticsengineeringubaya.R
import com.ubayadev.informaticsengineeringubaya.databinding.FragmentDetailBinding
import com.ubayadev.informaticsengineeringubaya.databinding.FragmentProfileBinding
import com.ubayadev.informaticsengineeringubaya.viewmodel.DetailViewModel
import com.ubayadev.informaticsengineeringubaya.viewmodel.ProfileViewModel

class FragmentDetail : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var idx=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.refresh(FragmentDetailArgs.fromBundle(requireArguments()).id.toString())
        observeViewModel()
        observePage()
        binding.refreshLayout.setOnRefreshListener {
            binding.txtDeskripsi.visibility = View.GONE
            binding.txtProgram.visibility=View.GONE
            binding.imgProgram.visibility = View.GONE
            binding.cardView.visibility=View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh(FragmentDetailArgs.fromBundle(requireArguments()).id.toString())
            binding.refreshLayout.isRefreshing = false
        }
        binding.btnNext.setOnClickListener{
            idx+=1
            observePage()
        }
        binding.btnPrev.setOnClickListener{
            idx-=1
            observePage()
        }
    }
    private fun observeViewModel() {
        viewModel.programLiveData.observe(viewLifecycleOwner, Observer {
            binding.txtDeskripsi.setText(it.deskripsi)
            binding.txtProgram.setText(it.nama)
            val builder = Picasso.Builder(requireContext())
            builder.listener{picasso, uri, exception->exception.printStackTrace()}
            builder.build().load(it.img_profil).into(binding.imgProgram)
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtDeskripsi.visibility = View.GONE
                binding.txtProgram.visibility=View.GONE
                binding.imgProgram.visibility = View.GONE
                binding.cardView.visibility=View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.txtDeskripsi.visibility = View.VISIBLE
                binding.txtProgram.visibility=View.VISIBLE
                binding.imgProgram.visibility = View.VISIBLE
                binding.cardView.visibility=View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
    private fun observePage(){
        viewModel.listPage.observe(viewLifecycleOwner, Observer {
            binding.txtDeskripsi.setText(it.get(idx))
            if (idx==it.size-1){
                binding.btnNext.isEnabled=false
                binding.btnPrev.isEnabled=true
            }
            else if (idx==0){
                binding.btnPrev.isEnabled=false
                binding.btnNext.isEnabled=true
            }
            else{
                binding.btnPrev.isEnabled=true
                binding.btnNext.isEnabled=true
            }
        })
    }

}