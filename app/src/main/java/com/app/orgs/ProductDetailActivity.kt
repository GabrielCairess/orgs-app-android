package com.app.orgs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.app.orgs.database.AppDatabase
import com.app.orgs.databinding.ActivityProductDetailBinding
import com.app.orgs.extensions.formatCurrency
import com.app.orgs.extensions.tryLoadImage
import com.app.orgs.model.Product
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private var productId: Long = 0L
    private lateinit var binding: ActivityProductDetailBinding
    private var product: Product? = null
    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            product = productDao.getById(productId)
            product?.let {
                fillFields(it)
            } ?: finish()
        }
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra(PRODUCT_KEY_ID, 0L)
    }

    private fun fillFields(product: Product) {
        with(binding) {
            detailImageProduct.tryLoadImage(product.image)
            detailNameProduct.text = product.name
            detailDescriptionProduct.text = product.description
            detailProductValue.text = product.value.formatCurrency()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_product_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.im_edit -> editProduct()
            R.id.im_delete -> deleteProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {

        lifecycleScope.launch {
            product?.let {
                productDao.delete(it)
            }
        }
        finish()
    }

    private fun editProduct() {
        Intent(this, FormProductActivity::class.java).apply {
            putExtra(PRODUCT_KEY_ID, productId)
            startActivity(this)
        }
    }
}