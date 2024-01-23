package com.app.orgs.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.app.orgs.databinding.FormImageBinding
import com.app.orgs.extensions.tryLoadImage

class FormImageDialog(private val context: Context) {

    fun showDialog(standardUrl: String? = null, whenLoadedImage: (image: String) -> Unit) {
        FormImageBinding.inflate(LayoutInflater.from(context)).apply {

            standardUrl?.let {
                formImageview.tryLoadImage(it)
                inpTextImage.setText(it)
            }

            btnLoadImage.setOnClickListener {
                val url = inpTextImage.text.toString()
                formImageview.tryLoadImage(url)
            }


            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = inpTextImage.text.toString()
                    whenLoadedImage(url)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }
}