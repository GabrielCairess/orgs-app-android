package com.app.orgs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.orgs.database.AppDatabase
import com.app.orgs.databinding.ActivityMainBinding
import com.app.orgs.ui.recyclerview.adapter.ProductListAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()
        binding.btnFormProduct.setOnClickListener(this)
    }

    private fun configRecyclerView() {
        val recyclerView = binding.reciclerviewProducts
        recyclerView.adapter = adapter
        adapter.whenClickItem = {
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, it)
            }

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.getInstance(this)
        val productDao = db.productDao()

        adapter.updateProductsList(productDao.getAll())
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_form_product) {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }
}