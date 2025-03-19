package com.example.traveltrip

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.discover)

//        findViewById<LinearLayout>(R.id.discover_hotels).setOnClickListener{
//            findViewById<ImageButton>(R.id.imageButton3).setImageResource(R.drawable.view1);
//        }
    }
}