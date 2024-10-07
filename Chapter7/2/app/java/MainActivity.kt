package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class MainActivity : AppCompatActivity() {
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        setupBarChart()
        setupPieChart()
    }

    private fun setupBarChart() {
        val barEntries = mutableListOf<BarEntry>()
        barEntries.add(BarEntry(1f, 10f))
        barEntries.add(BarEntry(2f, 20f))
        barEntries.add(BarEntry(3f, 30f))
        barEntries.add(BarEntry(4f, 40f))
        val barDataSet = BarDataSet(barEntries, "Bar Chart Example")
        barDataSet.color = Color.BLUE
        val barData = BarData(barDataSet)
        barChart.data = barData
        val description = Description()
        description.text = "Bar Chart Example"
        barChart.description = description
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun setupPieChart() {
        val pieEntries = mutableListOf<PieEntry>()
        pieEntries.add(PieEntry(40f, "Category 1"))
        pieEntries.add(PieEntry(30f, "Category 2"))
        pieEntries.add(PieEntry(20f, "Category 3"))
        pieEntries.add(PieEntry(10f, "Category 4"))
        val pieDataSet = PieDataSet(pieEntries, "Pie Chart Example")
        pieDataSet.colors = listOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
        )
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        val description = Description()
        description.text = "Pie Chart Example"
        pieChart.description = description
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}

