package com.an9ar.testauthapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.an9ar.testauthapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

fun EditText.afterTextChange(afterTextChange: (String) -> Unit){
    this.addTextChangedListener(object: TextWatcher{
        override fun afterTextChanged(text: Editable?) = afterTextChange.invoke(text.toString())
        override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

fun TextInputLayout.hideError(){
    this.error = null
}

fun showSnackbarResult(
    view: View,
    text: String,
    color: Int
    ){
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(color)
        .show()
}
