package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val editTextMessage = view.findViewById<EditText>(R.id.editText_message)
        val buttonSend = view.findViewById<Button>(R.id.button_send)
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()

            // SecondFragment로 메시지를 전달하고 프래그먼트를 교체합니다.
            val secondFragment = SecondFragment.newInstance(message)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, secondFragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}
