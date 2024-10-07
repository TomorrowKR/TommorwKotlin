package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var scannedResult: TextView
    private lateinit var productName: EditText
    private lateinit var productQuantity: EditText
    private lateinit var productAdapter: ProductAdapter
    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                scannedResult.text = result.contents
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scannedResult = findViewById(R.id.scannedResult)
        productName = findViewById(R.id.productName)
        productQuantity = findViewById(R.id.productQuantity)
        val scanButton: Button = findViewById(R.id.scanButton)
        val saveButton: Button = findViewById(R.id.saveButton)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        productAdapter = ProductAdapter()
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        productViewModel.allProducts.observe(this) { products ->
            products?.let { productAdapter.setProducts(it) }
        }
        scanButton.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Scan a barcode")
            options.setCameraId(0)
            options.setBeepEnabled(true)
            options.setBarcodeImageEnabled(true)
            barcodeLauncher.launch(options)
        }
        saveButton.setOnClickListener {
            val barcode = scannedResult.text.toString()
            val name = productName.text.toString()
            val quantity = productQuantity.text.toString().toIntOrNull()
            if (barcode.isNotEmpty() && name.isNotEmpty() && quantity !=
                null
            ) {
                val product = Product(barcode, name, quantity)
                productViewModel.insert(product)
                Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        scannedResult.text = ""
        productName.text.clear()
        productQuantity.text.clear()
    }
}


