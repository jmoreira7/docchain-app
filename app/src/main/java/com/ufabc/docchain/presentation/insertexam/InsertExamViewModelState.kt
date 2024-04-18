package com.ufabc.docchain.presentation.insertexam

import com.ufabc.docchain.presentation.shared.ActivityStatus
import com.ufabc.docchain.presentation.shared.ActivityStatus.NORMAL

data class InsertExamViewModelState(
    var insertExamStatus: ActivityStatus = NORMAL,
    var pdfIconVisible: Boolean = false
)