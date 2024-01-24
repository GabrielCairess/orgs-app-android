package com.app.orgs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.app.orgs.database.AppDatabase
import com.app.orgs.database.dao.ProductDao
import com.app.orgs.databinding.ActivityProductDetailBinding
import com.app.orgs.extensions.formatCurrency
import com.app.orgs.extensions.tryLoadImage
import com.app.orgs.model.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tryLoadProduct()
    }

    private fun tryLoadProduct() {
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let { loadedProduct ->
            product = loadedProduct
            fillFields(loadedProduct)
        } ?: finish()
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
        if (::product.isInitialized) {
            when (item.itemId) {
                R.id.im_edit -> editProduct()
                R.id.im_delete -> deleteProduct()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {
        val db = AppDatabase.getInstance(this)
        val productDao = db.productDao()
        productDao.delete(product)
        finish()
    }

    private fun editProduct() {
        Intent(this, FormProductActivity::class.java).apply {
            putExtra(PRODUCT_KEY, product)
            startActivity(this)
        }
    }
}