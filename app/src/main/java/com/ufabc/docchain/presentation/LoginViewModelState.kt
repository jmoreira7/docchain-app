package com.ufabc.docchain.presentation

import com.ufabc.docchain.presentation.ActivityStatus.*

data class LoginViewModelState(
    val loginStatus: ActivityStatus = NORMAL
)