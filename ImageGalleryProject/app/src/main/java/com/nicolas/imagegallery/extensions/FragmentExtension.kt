package com.nicolas.imagegallery.extensions

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.shortToast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.shortToast(@StringRes resId: Int) {
    Toast.makeText(this.context, getString(resId), Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_LONG).show()
}

fun Fragment.longToast(@StringRes resId: Int) {
    Toast.makeText(this.context, getString(resId), Toast.LENGTH_LONG).show()
}
