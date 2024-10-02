package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    private lateinit var mealListView: ListView
    private lateinit var addMealButton: Button
    private lateinit var searchEditText: EditText
    private lateinit var mealAdapter: MealAdapter
    private lateinit var addMealActivityResultLauncher:
            ActivityResultLauncher<Intent>
    private val meals = mutableListOf(
        Meal("Breakfast", "300 kcal", "Carbs: 50g, Protein: 20g, Fat: 10g"),
        Meal("Lunch", "600 kcal", "Carbs: 80g, Protein: 30g, Fat: 20g"),
        Meal("Dinner", "500 kcal", "Carbs: 70g, Protein: 25g, Fat: 15g")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mealListView = findViewById(R.id.meal_list_view)
        addMealButton = findViewById(R.id.add_meal_button)
        searchEditText = findViewById(R.id.search_edit_text)
        mealAdapter = MealAdapter(this, meals)
        mealListView.adapter = mealAdapter
        // ActivityResultLauncher 등록
        addMealActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val name = data?.getStringExtra("name") ?: return@registerForActivityResult
                val calories = data.getStringExtra("calories") ?: return@registerForActivityResult
                val nutrition = data.getStringExtra("nutrition") ?: return@registerForActivityResult
                val newMeal = Meal(name, calories, nutrition)
                meals.add(newMeal)
                mealAdapter.notifyDataSetChanged()
            }
        }
        addMealButton.setOnClickListener {
            val intent = Intent(this, AddMealActivity::class.java)
            addMealActivityResultLauncher.launch(intent)
        }
        searchEditText.addTextChangedListener {
            val query = it.toString()
            val filteredMeals = meals.filter { meal ->
                meal.name.contains(query, ignoreCase = true)
            }
            mealAdapter = MealAdapter(this, filteredMeals.toMutableList())
            mealListView.adapter = mealAdapter
        }
    }
}

