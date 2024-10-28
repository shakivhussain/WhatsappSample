package com.shakiv.whatsappsample.utils

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.shakiv.whatsappsample.utils.AppUtils.REQUEST_CODE_CAMERA
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart


fun EditText.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let {
        post {
            it.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }
}

fun EditText.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let {
        requestFocus()
        postDelayed({
            it.showSoftInput(this, 0)
        }, 300)
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }}



fun Fragment.openCamera(){
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)
}