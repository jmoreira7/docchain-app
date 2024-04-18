package com.ufabc.docchain.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    private var userId = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.exam_list)


        val exams = listOf<Exam>()

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ExamListAdapter(exams)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val updatedExams = blockchainRepository.getExams(this@ExamListActivity, userId)
            Log.d("DEBUG", "updatedExams: [$updatedExams]")
            adapter.updateExams(updatedExams)
        }
    }

    private fun retrieveExtra() {
        val userId = intent.getStringExtra("EXAM_LIST_ACTIVITY_INTENT_TAG")

        userId?.let {
            this.userId = userId
        }

    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
