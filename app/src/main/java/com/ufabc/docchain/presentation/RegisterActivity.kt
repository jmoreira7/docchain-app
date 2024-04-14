package com.ufabc.docchain.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.RegisterBinding

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
            val email = binding.registerEmailTextInputEditText.text.toString()
            val password = binding.registerPasswordTextInputEditText.text.toString()

            viewModel.createUser(email, password)
        }
    }

    private fun setupViewModel() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is RegisterViewModel.RegisterViewModelAction.showCreateUserSuccessDialog -> {
                    Toast.makeText(
                        this,
                        "Usuário criado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }

                is RegisterViewModel.RegisterViewModelAction.showCreateUserFailDialog -> {
                    Toast.makeText(
                        this,
                        "Criação de usuário falhou.",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }

    interface RegisterInterface {
        fun createUser(email: String, password: String)
    }
}