package com.ufabc.docchain.presentation

import com.ufabc.docchain.presentation.ActivityStatus.*

data class LoginViewModelState(
    var loginStatus: ActivityStatus = NORMAL
)