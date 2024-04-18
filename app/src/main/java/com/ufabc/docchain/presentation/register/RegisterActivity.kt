package com.ufabc.docchain.presentation.register

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.RegisterBinding
import com.ufabc.docchain.presentation.shared.ActivityStatus
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowCreateUserFailToast
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowCreateUserSuccessToast
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowEmptyEmailInputToast
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowEmptyIdInputToast
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowEmptyNameInputToast
import com.ufabc.docchain.presentation.register.RegisterViewModelAction.ShowEmptyPasswordInputToast

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.register)

        setupViews()
        setupViewModelEvents()
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

    private fun setupViewModelEvents() {
        viewModel.state.observe(this) { state ->
            handleViewModelState(state)
        }

        viewModel.action.observe(this) { action ->
            handleViewModelAction(action)
        }
    }

    private fun handleViewModelState(state: RegisterViewModelState) {
        updateRegisterActivityStatus(state.registerStatus)
    }

    private fun handleViewModelAction(action: RegisterViewModelAction) {
        when (action) {
            is ShowCreateUserSuccessToast -> {
                Toast.makeText(
                    this,
                    "Usuário criado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }

            is ShowCreateUserFailToast -> {
                Toast.makeText(
                    this,
                    "Criação de usuário falhou.",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }

            is ShowEmptyNameInputToast -> {
                Toast.makeText(
                    this,
                    "Insira um nome.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ShowEmptyIdInputToast -> {
                Toast.makeText(
                    this,
                    "Insira uma identificação.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ShowEmptyEmailInputToast -> {
                Toast.makeText(
                    this,
                    "Insira um e-mail.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ShowEmptyPasswordInputToast -> {
                Toast.makeText(
                    this,
                    "Insira uma senha.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateRegisterActivityStatus(status: ActivityStatus) {
        when (status) {
            ActivityStatus.NORMAL -> {
                binding.registerRegisterButton.visibility = VISIBLE
                binding.registerProgressBar.visibility = GONE
            }

            ActivityStatus.LOADING -> {
                binding.registerRegisterButton.visibility = GONE
                binding.registerProgressBar.visibility = VISIBLE
            }
        }
    }
}