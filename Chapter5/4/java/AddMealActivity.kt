package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddMealActivity : AppCompatActivity() {
    private lateinit var mealNameEditText: EditText
    private lateinit var mealCaloriesEditText: EditText
    private lateinit var mealNutritionEditText: EditText
    private lateinit var saveMealButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)
        mealNameEditText = findViewById(R.id.meal_name_edit_text)
        mealCaloriesEditText = findViewById(R.id.meal_calories_edit_text)
        mealNutritionEditText = findViewById(R.id.meal_nutrition_edit_text)
        saveMealButton = findViewById(R.id.save_meal_button)
        saveMealButton.setOnClickListener {
            saveMeal()
        }
    }

    private fun saveMeal() {
        val mealName = mealNameEditText.text.toString()
        val mealCalories = mealCaloriesEditText.text.toString()
        val mealNutrition = mealNutritionEditText.text.toString()
        if (mealName.isEmpty() || mealCalories.isEmpty() ||
            mealNutrition.isEmpty()
        ) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val resultIntent = Intent().apply {
                putExtra("name", mealName)
                putExtra("calories", mealCalories)
                putExtra("nutrition", mealNutrition)
            }
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}
