package com.ufabc.docchain.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.MenuBinding
import com.ufabc.docchain.presentation.LoginActivity.Companion.MENU_ACTIVITY_INTENT_TAG

class MenuActivity : AppCompatActivity() {

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var binding: MenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.menu)

        val loggedInUserName = intent.getStringExtra(MENU_ACTIVITY_INTENT_TAG)

        binding.menuUsernameText.text =
            getString(R.string.menu_activity_logged_in_user_text, loggedInUserName)
    }
}