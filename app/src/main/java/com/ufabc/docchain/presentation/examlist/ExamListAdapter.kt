package com.ufabc.docchain.presentation.examlist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ufabc.docchain.R
import com.ufabc.docchain.data.models.Exam

class ExamListAdapter(private var exams: List<Exam>) : RecyclerView.Adapter<ExamListAdapter.ExamViewHolder>() {

    class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val examName: TextView = itemView.findViewById(R.id.examName)
        val patientId: TextView = itemView.findViewById(R.id.patientId)
        val doctorId: TextView = itemView.findViewById(R.id.doctorId)
        val description: TextView = itemView.findViewById(R.id.description)

        fun bind(exam: Exam) {
            Log.d("ExamViewHolder", "Binding exam: $exam")
            examName.text = "${examName.text}${exam.examName}"
            patientId.text = "${patientId.text}${exam.patientId}"
            doctorId.text = "${doctorId.text}${exam.doctorId}"
            description.text = "${description.text}${exam.description}"

            itemView.setOnClickListener {
                if (exam.pdfUri != null){
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(exam.pdfUri.toString()), "application/pdf")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exam, parent, false)
        return ExamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exams.size
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(exams[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateExams(newExams: List<Exam>) {
        exams = newExams
        notifyDataSetChanged()
    }
}