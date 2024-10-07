package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var products = emptyList<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product, parent, false
        )
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = products[position]
        holder.bind(currentProduct)
    }

    override fun getItemCount() = products.size
    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        private val productName: TextView = itemView.findViewById(
            R.id.productName
        )
        private val productQuantity: TextView = itemView.findViewById(
            R.id.productQuantity
        )

        fun bind(product: Product) {
            productName.text = product.name
            productQuantity.text = "Quantity: ${product.quantity}"
        }
    }
}