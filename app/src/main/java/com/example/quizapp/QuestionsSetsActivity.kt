package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class QuestionsSetsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_questions_sets)


    }

    override fun onResume() {
        super.onResume()
        val fragment =
            supportFragmentManager.findFragmentById(R.id.questions_sets_fragment) as QuestionsSetListFragment

        fragment.setOnElementClickListener(object : QuestionsSetListAdapter.OnClickListener {
            override fun onItemClick(index: Int) {
               goToQuestionsSetEditor(fragment.getQuestionsSet(index))
            }
        })

        fragment.setOnLongElementClickListener(object : QuestionsSetListAdapter.OnLongClickListener {
            override fun onItemClick(index: Int) {
                openQuestionsSetsDialog(index)
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult","QSAActivity")
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun goToQuestionsList(index: Int) {
        val intent = Intent(this, QuestionsListActivity::class.java).putExtra("index", index)
        startActivity(intent)
    }

    fun openQuestionsSetSubmenu(index: Int) {
        val toast = Toast.makeText(applicationContext, "podmenu", Toast.LENGTH_SHORT)
        toast.show()

    }

    fun goToQuestionsSetEditor(questionsSet: QuestionsSet)
    {
        val intent = Intent(this, QuestionsSetsEditorActivity::class.java).putExtra("QS", questionsSet)
        startActivity(intent)

    }

    fun openQuestionsSetsDialog(index: Int)
    {
        val fragment = QuestionsSetDialogFragment()
        fragment.setQuestionsSet(getQuestionsSet(index))
        fragment.show(supportFragmentManager,"option")
        val rvFragment = supportFragmentManager.findFragmentById(R.id.questions_sets_fragment) as QuestionsSetListFragment
        rvFragment.selectedQuestionsSet = index
    }

    fun getQuestionsSet(index: Int):QuestionsSet
    {
        val fragment = supportFragmentManager.findFragmentById(R.id.questions_sets_fragment) as QuestionsSetListFragment
        val questionsSet = fragment.getQuestionsSet(index)
        return questionsSet
    }

}
