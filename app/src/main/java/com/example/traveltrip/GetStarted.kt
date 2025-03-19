package com.example.traveltrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class GetStarted : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.get_started, container, false)

        view.findViewById<Button>(R.id.getStarted_startBtn).setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()

            val registerFragment = RegisterFragment()
            fragmentTransaction.replace(R.id.startApp, registerFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return view
    }

}