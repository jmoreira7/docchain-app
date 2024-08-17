package com.ufabc.docchain.presentation.register

import com.ufabc.docchain.presentation.shared.ActivityStatus

data class RegisterViewModelState(
    var registerStatus: ActivityStatus = ActivityStatus.NORMAL
)