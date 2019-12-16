package com.example.quizapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.File

class SelectQuestionsSetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_select_questions_set)
            supportActionBar?.title = "Quizuj!"


    }
    override fun onResume()
    {
        super.onResume()
        val fragment = supportFragmentManager.findFragmentById(R.id.select_questions_sets_fragment) as QuestionsSetListFragment
        fragment.setOnElementClickListener(object : QuestionsSetListAdapter.OnClickListener
        {
            override fun onItemClick(index: Int)
            {
                goToQuestionsSetPlayer(fragment.getQuestionsSet(index))
            }
        })
    }
    fun goToQuestionsSetPlayer(questionsSet: QuestionsSet)
    {

        val intent = Intent(this, QuestionsSetPlayerActivity::class.java).putExtra("QS", questionsSet)
        startActivity(intent)

    }

}
