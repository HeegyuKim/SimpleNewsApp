package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions


@BindingAdapter(value=["imageUrl", "error", "placeholder"], requireAll = false)
fun loadImage(view: ImageView, url: String?, error: Drawable?, placeholder: Drawable?) {
    val options = RequestOptions()
        .error(error)
        .placeholder(placeholder)

    Glide.with(view)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(view)
}