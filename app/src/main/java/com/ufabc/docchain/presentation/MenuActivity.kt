package com.ufabc.docchain.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.MenuBinding
import com.ufabc.docchain.presentation.LoginActivity.Companion.MENU_ACTIVITY_INTENT_TAG

class MenuActivity : AppCompatActivity() {

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var binding: MenuBinding

    private var userId = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.menu)

        retrieveExtra()
        setupViews()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.state.observe(this) { state ->
            this.userId = state.userId
            Log.d("DEBUG", "MenuActivity userId updated: [${state.userId}]")
        }
    }

    private fun setupViews() {
        binding.menuInsertExamDataButton.setOnClickListener {
            val intent = Intent(this, InsertExamActivity::class.java)

            startActivity(intent)
        }

        binding.menuConsultExamDataButton.setOnClickListener {
            val intent = Intent(this, ExamListActivity::class.java)
            Log.d("DEBUG", "MenuActivity userId: [$userId]")
            intent.putExtra("EXAM_LIST_ACTIVITY_INTENT_TAG", userId)
            startActivity(intent)
        }
    }

    private fun retrieveExtra() {
        val userAuthId = intent.getStringExtra(MENU_ACTIVITY_INTENT_TAG)

        userAuthId?.let {
            viewModel.setUserAuthUid(it)
        }
    }

    fun updateUserId(userId: String) {
        this.userId = userId
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}