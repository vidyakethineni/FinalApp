package com.example.finalapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalapp.MainActivity
import com.example.finalapp.R
import com.example.finalapp.databinding.FragmentLoginBinding
import com.example.finalapp.models.Signup

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.signupTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        binding.loginBtn.setOnClickListener {
            val pair = viewModel.isDetailsValidated(viewModel.mSignupLiveData.value ?: Signup())
            if (pair.first) {
                viewModel.performLogin(
                    viewModel.mSignupLiveData.value ?: return@setOnClickListener
                )
            } else {
                Toast.makeText(requireContext(), pair.second, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.resultLiveData.observe(viewLifecycleOwner){
            it?.apply {
                if(it.isSuccess){
                    startActivity(Intent(requireActivity(),MainActivity::class.java))
                    requireActivity().finish()
                }else{
                    Toast.makeText(requireContext(), it.exceptionOrNull()?.localizedMessage?:"Invalid Credentials!", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}