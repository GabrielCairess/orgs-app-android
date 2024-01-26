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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormProductBinding
    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
    }
    private var url: String? = null
    private var productId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormProductBinding.inflate(layoutInflater)
        binding.btnSave.setOnClickListener(this)
        binding.imgProduct.setOnClickListener(this)

        tryLoadProduct()

        setContentView(binding.root)
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0L)
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            productDao.getById(productId)?.let {
                withContext(Dispatchers.Main) {
                    fillFields(it)
                }
            }
        }
    }

    private fun fillFields(product: Product) {
        title = "Alterar Produto"
        url = product.image
        binding.imgProduct.tryLoadImage(product.image)
        binding.edtName.setText(product.name)
        binding.edtDescription.setText(product.description)
        binding.edtValue.setText(product.value.toPlainString())

        binding.btnSave.text = "Atualizar"
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
            CoroutineScope(Dispatchers.IO).launch {
                productDao.save(Product(id = productId, name = name, description = description, value = BigDecimal(value), image = url))
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Campo(s) Obrigatórios não preenchidos", Toast.LENGTH_LONG).show()
        }
    }
}