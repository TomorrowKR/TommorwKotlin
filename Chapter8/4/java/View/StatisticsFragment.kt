package com.example.myapplication.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ViewModel.HealthViewModel
import com.example.myapplication.databinding.FragmentStatisticsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var healthViewModel: HealthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        healthViewModel = ViewModelProvider(this).get(
            HealthViewModel::class.java
        )
        val currentMonth = getCurrentMonth() // 현재	월	가져오기
        healthViewModel.getMonthlyStatistics(currentMonth)
            .observe(viewLifecycleOwner, Observer { stats ->
                binding.totalExerciseText.text = "총 운동 시간: ${stats.totalExercise} 분"
                binding.totalCaloriesText.text = "총 섭취 칼로리: ${stats.totalCalories} kcal"
                binding.totalSleepText.text = "총 수면 시간: ${stats.totalSleep} 시간"
            })
    }

    private fun getCurrentMonth(): String {
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return sdf.format(Date())
    }
}