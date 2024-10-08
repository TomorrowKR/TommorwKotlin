package com.example.myapplication.View

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Model.HealthData
import com.example.myapplication.R
import com.example.myapplication.ViewModel.HealthViewModel
import com.example.myapplication.databinding.FragmentHealthTrackerBinding

class HealthTrackerFragment : Fragment() {
    private lateinit var binding: FragmentHealthTrackerBinding
    private lateinit var healthViewModel: HealthViewModel
    private lateinit var healthDataAdapter: HealthDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthTrackerBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        healthViewModel = ViewModelProvider(this).get(
            HealthViewModel::class.java
        )
        // RecyclerView 설정
        healthDataAdapter = HealthDataAdapter(
            onDeleteClick = { healthData ->
                healthViewModel.deleteHealthData(healthData)
            },
            onEditClick = { healthData -> showEditDialog(healthData) }
        )
        binding.dataList.adapter = healthDataAdapter
        binding.dataList.layoutManager = LinearLayoutManager(context)
        // LiveData를	관찰하여	데이터	업데이트
        healthViewModel.allHealthData.observe(viewLifecycleOwner,
            Observer { healthDataList ->
                healthDataAdapter.submitList(healthDataList)
            })
        // 데이터	추가	버튼	클릭	이벤트	처리
        binding.addButton.setOnClickListener {
            addNewData()
        }
        // 통계	화면으로	이동하는	버튼	클릭	이벤트	처리
        binding.statisticsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_healthTrackerFragment_to_statisticsFragment
            )
        }
    }

    private fun addNewData() {
        val exerciseDurationStr = binding.exerciseInput.text.toString()
        val foodCaloriesStr = binding.foodInput.text.toString()
        val sleepDurationStr = binding.sleepInput.text.toString()
        if (exerciseDurationStr.isBlank() || foodCaloriesStr.isBlank() ||
            sleepDurationStr.isBlank()
        ) {
            Toast.makeText(
                context, "모든 필드를 입력하세요.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val exerciseDuration = exerciseDurationStr.toIntOrNull()
        val foodCalories = foodCaloriesStr.toIntOrNull()
        val sleepDuration = sleepDurationStr.toIntOrNull()
        if (exerciseDuration == null || foodCalories == null ||
            sleepDuration == null
        ) {
            Toast.makeText(
                context, "유효한 숫자를 입력하세요.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val healthData = HealthData(
            date = System.currentTimeMillis(),
            exerciseDuration = exerciseDuration,
            foodCalories = foodCalories,
            sleepDuration = sleepDuration
        )
        healthViewModel.insertHealthData(healthData)
        Toast.makeText(
            context, "데이터가 성공적으로 추가되었습니다.",
            Toast.LENGTH_SHORT
        ).show()
        // 입력	필드	초기화
        binding.exerciseInput.text.clear()
        binding.foodInput.text.clear()
        binding.sleepInput.text.clear()
    }

    private fun showEditDialog(healthData: HealthData) {
        context?.let { nonNullContext ->
            val dialogView = LayoutInflater.from(nonNullContext).inflate(
                R.layout.dialog_edit_health_data, null
            )
            val dialogBuilder = AlertDialog.Builder(nonNullContext)
                .setView(dialogView)
                .setTitle("데이터 수정")
                .setPositiveButton("저장") { dialog, _ ->
                    val exerciseDurationStr =
                        dialogView.findViewById<EditText>(R.id.exercise_input).text.toString()
                    val foodCaloriesStr =
                        dialogView.findViewById<EditText>(R.id.food_input).text.toString()
                    val sleepDurationStr =
                        dialogView.findViewById<EditText>(R.id.sleep_input).text.toString()
                    if (exerciseDurationStr.isBlank() || foodCaloriesStr
                            .isBlank() || sleepDurationStr.isBlank()
                    ) {
                        Toast.makeText(nonNullContext, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    val exerciseDuration = exerciseDurationStr
                        .toIntOrNull()
                    val foodCalories = foodCaloriesStr.toIntOrNull()
                    val sleepDuration = sleepDurationStr.toIntOrNull()
                    if (exerciseDuration == null || foodCalories == null ||
                        sleepDuration == null
                    ) {
                        Toast.makeText(nonNullContext, "유효한 숫자를 입력하세요.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    val updatedHealthData = healthData.copy(
                        exerciseDuration = exerciseDuration,
                        foodCalories = foodCalories,
                        sleepDuration = sleepDuration
                    )
                    healthViewModel.updateHealthData(updatedHealthData)
                    Toast.makeText(nonNullContext, "데이터가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
            dialogView.findViewById<EditText>(R.id.exercise_input)
                .setText(healthData.exerciseDuration.toString())
            dialogView.findViewById<EditText>(R.id.food_input)
                .setText(healthData.foodCalories.toString())
            dialogView.findViewById<EditText>(R.id.sleep_input)
                .setText(healthData.sleepDuration.toString())
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }
}