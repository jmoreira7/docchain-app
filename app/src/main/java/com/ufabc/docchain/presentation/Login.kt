package com.ufabc.docchain.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.LoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.login)

        setupViews()
    }

    private fun setupViews() {
        binding.loginRegisterButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding.loginLoginButton.setOnClickListener {
            validateInputs()
        }
    }

    private fun validateInputs() {
        val email: String = binding.loginUserTextInputEditText.text.toString()
        val password: String = binding.loginPasswordTextInputEditText.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.login_activity_email_validation_empty_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.login_activity_password_validation_empty_message),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
    }
}