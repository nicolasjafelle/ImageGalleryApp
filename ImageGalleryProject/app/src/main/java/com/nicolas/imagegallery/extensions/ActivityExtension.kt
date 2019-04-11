package com.nicolas.imagegallery.extensions

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity

/**
 * Created by nicolas on 11/8/17.
 */
fun FragmentActivity.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.shortToast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.longToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun FragmentActivity.longToast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show()
}

fun FragmentActivity.hideSoftKeyboard() {
    val imm = this.getSystemService(
        android.content.Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    val currentFocus = this.currentFocus
    if (currentFocus != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun FragmentActivity.showSoftKeyboard() {
    val imm = this.getSystemService(
        android.content.Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    val currentFocus = this.currentFocus
    if (currentFocus != null) {
        imm.showSoftInput(currentFocus, 0)
    }
}


