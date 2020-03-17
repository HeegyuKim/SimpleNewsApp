package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("android:visibility")
fun ViewVisibilityExtension(
    view: View,
    visibility: Boolean
){
    view.visibility == if(visibility) View.VISIBLE else View.INVISIBLE
}