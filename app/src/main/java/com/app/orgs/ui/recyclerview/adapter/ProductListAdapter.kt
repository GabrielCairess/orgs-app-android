package com.app.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.orgs.R
import com.app.orgs.databinding.ProductItemBinding
import com.app.orgs.extensions.formatCurrency
import com.app.orgs.extensions.tryLoadImage
import com.app.orgs.model.Product
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ProductListAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var whenClickItem: (product: Product) -> Unit = {},
    var whenClickEdit: (product: Product) -> Unit = {},
    var whenClickDelete: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.attachProduct(product)
    }

    fun updateProductsList(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    whenClickItem(product)
                }
            }

            itemView.setOnLongClickListener {
                PopupMenu(context, itemView).apply {
                    menuInflater.inflate(R.menu.detail_product_menu, menu)
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                true
            }
        }



        fun attachProduct(product: Product) {
            this.product = product
            binding.productName.text = product.name
            binding.productDescription.text = product.description
            binding.productValue.text = product.value.formatCurrency()
            binding.productImage.tryLoadImage(product.image)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.im_edit -> whenClickEdit(product)
                    R.id.im_delete -> whenClickDelete(product)
                }
            }
            return true
        }
    }
}
