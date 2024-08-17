package com.ufabc.docchain.presentation.menu

import android.content.Context

interface MenuEvent {
    fun setUserAuthUid(authUid: String)

    fun consultExams(context: Context)
}