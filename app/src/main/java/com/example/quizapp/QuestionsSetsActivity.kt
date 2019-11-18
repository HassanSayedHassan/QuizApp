package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class QuestionsSetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_sets)




    }

    override fun onResume() {
        super.onResume()
        val fragment = supportFragmentManager.findFragmentById(R.id.questions_sets_fragment) as QuestionsSetListFragment

        fragment.setOnClickListener(object : QuestionsSetListAdapter.OnClickListener{
            override fun onItemClick(index: Int) {
                goToQuestionsList(index)
            }
        })

        fragment.setOnLongClickListener(object :QuestionsSetListAdapter.OnLongClickListener{
            override fun onItemClick(index: Int) {
                openQuestionsSetSubmenu(index)
            }
        })
    }

    fun goToQuestionsList(index: Int)
    {
        val intent = Intent(this,QuestionsListActivity::class.java).apply { index }
        startActivity(intent)
    }
    fun openQuestionsSetSubmenu(index: Int)
    {
        val toast = Toast.makeText(applicationContext,"podmenu",Toast.LENGTH_SHORT)
        toast.show()

    }


}
