package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test_log", "testing new branch")
        setContentView(R.layout.activity_main)



    }
    fun click(view: View)
    {
        val toast = Toast.makeText(
            applicationContext,
            "próba mikrofonu",
            Toast.LENGTH_SHORT
        )

        toast.show()

    }
}
