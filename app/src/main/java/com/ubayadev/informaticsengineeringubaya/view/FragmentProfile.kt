package com.ubayadev.informaticsengineeringubaya.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubayadev.informaticsengineeringubaya.R
import com.ubayadev.informaticsengineeringubaya.databinding.FragmentHomeBinding
import com.ubayadev.informaticsengineeringubaya.databinding.FragmentProfileBinding
import com.ubayadev.informaticsengineeringubaya.viewmodel.HomeViewModel
import com.ubayadev.informaticsengineeringubaya.viewmodel.ProfileViewModel

class FragmentProfile : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.refresh()
        observeViewModel()

        binding.btnLogout.setOnClickListener{
            getActivity()?.finish()
        }

        binding.btnChangePassword.setOnClickListener{
            if (binding.txtConfirm.text.toString()=="" || binding.txtNewPassword.text.toString()==""){
                Toast.makeText(requireContext(), "Isi password baru terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
            else if (binding.txtNewPassword.text.toString()==binding.txtConfirm.text.toString()){
                viewModel.changePassword(binding.txtNewPassword.text.toString())
                binding.txtPassword.setText(binding.txtNewPassword.text.toString())
                binding.txtNewPassword.setText("")
                binding.txtConfirm.setText("")
                Toast.makeText(requireContext(), "Berhasil mengganti password", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Konfirmasi password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.txtUsername.visibility = View.GONE
            binding.txtNamaDepan.visibility = View.GONE
            binding.txtNamaBelakang.visibility = View.GONE
            binding.txtPassword.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }
    private fun observeViewModel() {
        viewModel.accountLiveData.observe(viewLifecycleOwner, Observer {
            binding.txtUsername.setText(it.username)
            binding.txtNamaDepan.setText(it.nama_depan)
            binding.txtNamaBelakang.setText(it.nama_belakang)
            binding.txtPassword.setText(it.password)
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtUsername.visibility = View.GONE
                binding.txtNamaDepan.visibility = View.GONE
                binding.txtNamaBelakang.visibility = View.GONE
                binding.txtPassword.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.txtUsername.visibility = View.VISIBLE
                binding.txtNamaDepan.visibility = View.VISIBLE
                binding.txtNamaBelakang.visibility = View.VISIBLE
                binding.txtPassword.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }

}