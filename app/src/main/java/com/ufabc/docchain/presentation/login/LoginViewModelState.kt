package com.ufabc.docchain.presentation.login

import com.ufabc.docchain.presentation.shared.ActivityStatus
import com.ufabc.docchain.presentation.shared.ActivityStatus.*

data class LoginViewModelState(
    val loginStatus: ActivityStatus = NORMAL
)