package kr.heegyu.simplenewsapp.android.ui.common.binding

import androidx.databinding.BindingConversion
import android.net.Uri


@BindingConversion
fun stringToUri(str: String): Uri = Uri.parse(str)