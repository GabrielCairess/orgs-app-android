package com.app.orgs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.app.orgs.databinding.ActivityProductDetailBinding
import com.app.orgs.extensions.formatCurrency
import com.app.orgs.extensions.tryLoadImage
import com.app.orgs.model.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tryLoadProduct()
    }

    private fun tryLoadProduct() {
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let { loadedProduct ->
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
        when (item.itemId) {
            R.id.im_edit -> editProduct()
            R.id.im_delete -> deleteProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {
        Toast.makeText(this, "DELETANDOOO", Toast.LENGTH_LONG).show()
    }

    private fun editProduct() {
        Toast.makeText(this, "EDITANDOOOO", Toast.LENGTH_LONG).show()
    }
}