package com.ufabc.docchain.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ufabc.docchain.R
import com.ufabc.docchain.databinding.InsertExamDataBinding

class InsertExamActivity : AppCompatActivity() {

    private val viewModel: InsertExamViewModel by viewModels()

    private lateinit var binding: InsertExamDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.insert_exam_data)

        requestPermissions()
        setupViews()
        setupViewModel()
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun setupViews() {
        binding.insertExamDataCancelButton.setOnClickListener { finish() }

        binding.insertExamDataAddExamButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, REQUEST_CODE_PICK_PDF)
        }

        binding.insertExamDataApplyButton.setOnClickListener {
            viewModel.sendExamData(
                context = this,
                patientId = binding.insertExamDataIdPatientTextInputEditText.text.toString(),
                doctorId = binding.insertExamDataIdDoctorTextInputEditText.text.toString(),
                examName = binding.insertExamDataExamNameTextInputEditText.text.toString(),
                description = binding.insertExamDataDescriptionTextInputEditText.text.toString()
            )
        }
    }

    private fun setupViewModel() {
        viewModel.state.observe(this) { state ->
            if (state.pdfIconVisible) {
                binding.insertExamDataExamIcon.visibility = VISIBLE
            } else {
                binding.insertExamDataExamIcon.visibility = GONE
            }

            when (state.insertExamStatus) {
                ActivityStatus.NORMAL -> {
                    binding.insertExamDataTitle.visibility = VISIBLE
                    binding.insertExamDataExamNameTextInput.visibility = VISIBLE
                    binding.insertExamDataIdPatientTextInput.visibility = VISIBLE
                    binding.insertExamDataIdDoctorTextInput.visibility = VISIBLE
                    binding.insertExamDataDescriptionTextInput.visibility = VISIBLE
                    binding.insertExamDataAddExamTitle.visibility = VISIBLE
                    binding.insertExamDataAddExamButton.visibility = VISIBLE
                    binding.insertExamDataApplyButton.visibility = VISIBLE
                    binding.insertExamDataProgressBar.visibility = GONE
                    binding.insertExamDataCancelButton.visibility = VISIBLE
                }
                ActivityStatus.LOADING -> {
                    binding.insertExamDataTitle.visibility = GONE
                    binding.insertExamDataExamNameTextInput.visibility = GONE
                    binding.insertExamDataIdPatientTextInput.visibility = GONE
                    binding.insertExamDataIdDoctorTextInput.visibility = GONE
                    binding.insertExamDataDescriptionTextInput.visibility = GONE
                    binding.insertExamDataAddExamTitle.visibility = GONE
                    binding.insertExamDataAddExamButton.visibility = GONE
                    binding.insertExamDataApplyButton.visibility = GONE
                    binding.insertExamDataExamIcon.visibility = GONE
                    binding.insertExamDataExamFileTitle.visibility = GONE
                    binding.insertExamDataProgressBar.visibility = VISIBLE
                    binding.insertExamDataCancelButton.visibility = GONE
                }
            }
        }

        viewModel.action.observe(this) { action ->
            when (action) {
                InsertExamViewModelAction.ShowFailToast -> {
                    Toast.makeText(this, "Falhou.", Toast.LENGTH_SHORT).show()
                }
                InsertExamViewModelAction.ShowSuccessToast -> {
                    Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()

                    finish()
                }

                InsertExamViewModelAction.ShowEmptyInputFieldToast -> TODO()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.d(
                        LOG_TAG,
                        "Permission granted for the following permission types: [$permissions]"
                    )
                }
                return
            }

            else -> {
                // Do nothing.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_PDF && resultCode == Activity.RESULT_OK) {
            val selectedPdfUri: Uri? = data?.data
            viewModel.pdfFileSelected(selectedPdfUri)

            Log.d(LOG_TAG, "A pdf file was selected successfully. Uri: [$selectedPdfUri]")
        }
    }

    companion object {
        private const val LOG_TAG = "InsertExamActivity"
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
        private const val REQUEST_CODE_PICK_PDF = 2
    }
}