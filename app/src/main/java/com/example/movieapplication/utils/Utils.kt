package com.example.movieapplication.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    /**
     * Extension function to hide the keyboard
     */
    fun hideSoftKeyboard(context: Context, view:View) {
        try {
            val inm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            Log.e("Utils", "ERROR - ${e.message}")
            e.printStackTrace()
        }
    }
}