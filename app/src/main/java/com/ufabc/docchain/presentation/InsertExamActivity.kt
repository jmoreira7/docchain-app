package com.ufabc.docchain.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.InsertExamDataBinding

class InsertExamActivity : AppCompatActivity() {

    private lateinit var binding: InsertExamDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.insert_exam_data)

        setupViews()
    }

    private fun setupViews() {
        binding.insertExamDataCancelButton.setOnClickListener { finish() }
    }
}