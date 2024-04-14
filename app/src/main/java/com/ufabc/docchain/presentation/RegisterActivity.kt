package com.ufabc.docchain.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.RegisterBinding
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserFailToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showCreateUserSuccessToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyEmailInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyIdInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyNameInputToast
import com.ufabc.docchain.presentation.RegisterViewModel.RegisterViewModelAction.showEmptyPasswordInputToast

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.register)

        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        binding.registerBackButton.setOnClickListener {
            finish()
        }

        binding.registerRegisterButton.setOnClickListener {
            val name = binding.registerNameTextInputEditText.text.toString()
            val id = binding.registerIdTextInputEditText.text.toString()
            val email = binding.registerEmailTextInputEditText.text.toString()
            val password = binding.registerPasswordTextInputEditText.text.toString()

            viewModel.submitRegistration(name, id, email, password)
        }
    }

    private fun setupViewModel() {
        viewModel.action.observe(this) { action ->
            handleViewModelAction(action)
        }
    }

    private fun handleViewModelAction(action: RegisterViewModelAction) {
        when (action) {
            is showCreateUserSuccessToast -> {
                Toast.makeText(
                    this,
                    "Usuário criado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }

            is showCreateUserFailToast -> {
                Toast.makeText(
                    this,
                    "Criação de usuário falhou.",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }

            is showEmptyNameInputToast -> {
                Toast.makeText(
                    this,
                    "Insira um nome.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is showEmptyIdInputToast -> {
                Toast.makeText(
                    this,
                    "Insira uma identificação.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is showEmptyEmailInputToast -> {
                Toast.makeText(
                    this,
                    "Insira um e-mail.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is showEmptyPasswordInputToast -> {
                Toast.makeText(
                    this,
                    "Insira uma senha.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    interface RegisterInterface {
        fun submitRegistration(name: String, id: String, email: String, password: String)
    }
}