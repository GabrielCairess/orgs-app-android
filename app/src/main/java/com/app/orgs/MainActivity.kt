package com.app.orgs

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.orgs.database.AppDatabase
import com.app.orgs.databinding.ActivityMainBinding
import com.app.orgs.model.Product
import com.app.orgs.ui.recyclerview.adapter.ProductListAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductListAdapter(this)

    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()
        binding.btnFormProduct.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_product_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val orderedProducts: List<Product>? = when (item.itemId) {
            R.id.order_by_name_desc_menu -> productDao.getAllOrderByNameDesc()
            R.id.order_by_name_asc_menu -> productDao.getAllOrderByNameAsc()
            R.id.order_by_description_desc_menu -> productDao.getAllOrderByDescriptionDesc()
            R.id.order_by_description_asc_menu -> productDao.getAllOrderByDescriptionAsc()
            R.id.order_by_value_desc_menu -> productDao.getAllOrderByValueDesc()
            R.id.order_by_value_asc_menu -> productDao.getAllOrderByValueAsc()
            R.id.without_order_menu -> productDao.getAll()
            else -> null
        }

        orderedProducts?.let {
            adapter.updateProductsList(it)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configRecyclerView() {
        val recyclerView = binding.reciclerviewProducts
        recyclerView.adapter = adapter
        adapter.whenClickItem = {
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY_ID, it.id)
            }

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateProductsList(productDao.getAll())
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_form_product) {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }
}