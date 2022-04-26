package com.nokhba.center.panel.utils

import android.app.Activity
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.android.material.snackbar.Snackbar
import com.nokhba.center.panel.R
import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_DATE = "dd/MM/yyyy"
const val FIRE_ID_DATE = "ddMMyy"


fun Date.format(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun String.format(): String = StringBuilder(this).insert(2, "/").insert(5, "/").toString()

fun View.showSnack(msg: String) = Snackbar.make(this, msg, Snackbar.LENGTH_LONG).apply { show() }

fun View.showSnackAction(msg: String, action: String) =
    Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE).apply {
        this.setAction(action) { dismiss() }
        show()
    }


fun ActionBar.addIcon(): ActionBar = this.apply {
    setDisplayShowTitleEnabled(true)
    setDisplayHomeAsUpEnabled(true) // switch on the left hand icon
    setHomeAsUpIndicator(R.drawable.ic_logo)
}