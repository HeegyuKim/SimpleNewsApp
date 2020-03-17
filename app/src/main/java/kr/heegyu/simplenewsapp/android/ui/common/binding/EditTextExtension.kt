package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.text.TextWatcher
import android.widget.EditText


@BindingAdapter("onTextChanged", requireAll = false)
fun EditTextExtension(
    editText: EditText,
    textWatcher: TextWatcher?
    ){

    textWatcher?.let(editText::addTextChangedListener)
}