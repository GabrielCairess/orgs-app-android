package com.app.orgs.extensions

import android.widget.ImageView
import coil.load
import com.app.orgs.R

fun ImageView.tryLoadImage(url: String? = null, fallback: Int = R.drawable.imagem_padrao) {
    load(url) {
        fallback(fallback)
        error(R.drawable.imagem_padrao)
        placeholder(R.drawable.ic_action_refresh)
    }
}