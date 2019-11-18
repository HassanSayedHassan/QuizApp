package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View


class MainMenuActivity : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)



    }

    fun openSelectQuestionsSetActivity(view: View)
    {
        val intent = Intent(this,SelectQuestionsSetActivity::class.java)
        startActivity(intent)

    }
    fun openQuestionsSetsActivity(view: View)
    {
        val intent = Intent(this,QuestionsSetsActivity::class.java)
        startActivity(intent)

    }
}
