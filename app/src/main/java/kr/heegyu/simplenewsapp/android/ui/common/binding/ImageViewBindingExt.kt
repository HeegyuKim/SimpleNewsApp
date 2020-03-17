package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView


//@BindingAdapter(value=["imageUrl", "error", "placeholder"], requireAll = false)
//fun loadImage(view: ImageView, url: String?, error: Drawable?, placeholder: Drawable?) {
//    val options = RequestOptions()
//        .error(error)
//        .placeholder(placeholder)
//
//    Glide.with(view)
//        .setDefaultRequestOptions(options)
//        .load(url)
//        .into(view)
//}


@BindingAdapter(value = ["imageUrl"], requireAll = false)
fun SimpleDraweeView.bind(
    url: String?
) {
    setImageURI(url)
}