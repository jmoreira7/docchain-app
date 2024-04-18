package com.ufabc.docchain.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.docchain.R
import com.ufabc.docchain.data.BlockchainRepositoryI
import com.ufabc.docchain.data.BlockchainRepositoryImpl
import com.ufabc.docchain.data.Exam
import com.ufabc.docchain.databinding.ExamListBinding
import kotlinx.coroutines.launch

class ExamListActivity : AppCompatActivity() {

    private lateinit var binding: ExamListBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: ExamListAdapter

    private val blockchainRepository: BlockchainRepositoryI = BlockchainRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.exam_list)

        val exams = listOf<Exam>(Exam(
            "examName",
            "patientId",
            "doctorId",
            "description",
            null
        ))

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ExamListAdapter(exams)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val updatedExams = blockchainRepository.getExams(this@ExamListActivity, "13898633799")
            Log.d("DEBUG", "updatedExams: [$updatedExams]")
            adapter.updateExams(updatedExams)
        }
    }
}