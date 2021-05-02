package com.afrakhteh.dogsapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.afrakhteh.dogsapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.load(uri: String?, progressDrawable: CircularProgressDrawable) {
    val option = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.error)

    Glide.with(context).setDefaultRequestOptions(option).load(uri).into(this)
}

@BindingAdapter("android:imageuri")
fun loadImage(view: ImageView, uri: String?) {
    view.load(uri, getProgressDrawable(view.context))
}