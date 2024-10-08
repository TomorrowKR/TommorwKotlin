package com.example.myapplication.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.HealthData
import com.example.myapplication.databinding.ItemHealthDataBinding

class HealthDataAdapter(
    private val onDeleteClick: (HealthData) -> Unit,
    private val onEditClick: (HealthData) -> Unit
) : ListAdapter<HealthData, HealthDataAdapter.HealthDataViewHolder>
    (HealthDataDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HealthDataViewHolder {
        val binding = ItemHealthDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HealthDataViewHolder(binding, onDeleteClick, onEditClick)
    }

    override fun onBindViewHolder(
        holder: HealthDataViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class HealthDataViewHolder(
        private val binding: ItemHealthDataBinding,
        private val onDeleteClick: (HealthData) -> Unit,
        private val onEditClick: (HealthData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(healthData: HealthData) {
            binding.exerciseDurationText.text = "운동 시간: ${healthData.exerciseDuration} 분"
            binding.foodCaloriesText.text = "섭취 칼로리: ${healthData.foodCalories} kcal"
            binding.sleepDurationText.text = "수면 시간: ${healthData.sleepDuration} 시간"
            binding.deleteButton.setOnClickListener {
                onDeleteClick(healthData)
            }
            binding.editButton.setOnClickListener {
                onEditClick(healthData)
            }
        }
    }

    class HealthDataDiffCallback : DiffUtil.ItemCallback<HealthData>() {
        override fun areItemsTheSame(
            oldItem: HealthData, newItem:
            HealthData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HealthData, newItem:
            HealthData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
