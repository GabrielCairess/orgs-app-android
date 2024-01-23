package com.app.orgs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.orgs.database.AppDatabase
import com.app.orgs.database.dao.ProductDao
import com.app.orgs.databinding.ActivityFormProductBinding
import com.app.orgs.dialog.FormImageDialog
import com.app.orgs.extensions.tryLoadImage
import com.app.orgs.model.Product
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormProductBinding
    private lateinit var productDao: ProductDao
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormProductBinding.inflate(layoutInflater)
        binding.btnSave.setOnClickListener(this)
        binding.imgProduct.setOnClickListener(this)

        val db = AppDatabase.getInstance(this)
        productDao = db.productDao()

        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save -> addProduct()
            R.id.img_product -> FormImageDialog(this).showDialog(url) { image ->
                url = image
                binding.imgProduct.tryLoadImage(url)
            }
        }
    }

    private fun addProduct() {
        val name = binding.edtName.text.toString()
        val description = binding.edtDescription.text.toString()
        val value = binding.edtValue.text.toString()

        if (!name.isNullOrEmpty() && !description.isNullOrEmpty() && !value.isNullOrEmpty()) {
            productDao.save(Product(name = name, description = description, value = BigDecimal(value)))
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Campo(s) Obrigatórios não preenchidos", Toast.LENGTH_LONG).show()
        }
    }
}