package com.ufabc.docchain.presentation

import com.ufabc.docchain.presentation.ActivityStatus.NORMAL

data class InsertExamViewModelState(
    var insertExamStatus: ActivityStatus = NORMAL,
    var pdfIconVisible: Boolean = false
)