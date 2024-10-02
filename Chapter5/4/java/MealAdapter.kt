package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MealAdapter(
    private val context: Context, private val meals:
    MutableList<Meal>
) : BaseAdapter() {
    override fun getCount(): Int {
        return meals.size
    }

    override fun getItem(position: Int): Any {
        return meals[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int, convertView: View?, parent:
        ViewGroup?
    ): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_meal, parent, false)
        val meal = meals[position]
        val mealNameTextView = view.findViewById<TextView>(R.id.meal_name_text_view)
        val mealCaloriesTextView = view.findViewById<TextView>(R.id.meal_calories_text_view)
        val mealNutritionTextView = view.findViewById<TextView>(R.id.meal_nutrition_text_view)
        mealNameTextView.text = meal.name
        mealCaloriesTextView.text = meal.calories
        mealNutritionTextView.text = meal.nutrition
        return view
    }
}
