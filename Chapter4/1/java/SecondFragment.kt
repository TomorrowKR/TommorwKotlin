package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {
    companion object {
        private const val ARG_MESSAGE = "message"
        fun newInstance(message: String): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        // 전달된 메시지를 받아서 TextView에 표시합니다.
        val message = arguments?.getString(ARG_MESSAGE)
        val textViewMessage = view.findViewById<TextView>(R.id.textView_message)
        textViewMessage.text = message
        return view
    }
}
