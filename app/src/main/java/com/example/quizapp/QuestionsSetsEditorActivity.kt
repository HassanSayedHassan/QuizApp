package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText

class QuestionsSetsEditorActivity : AppCompatActivity() {

    companion object {
        private const val QUESTION = 100
    }
    private var questionsSet: QuestionsSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_sets_editor)



        if(intent.hasExtra("QS"))
            questionsSet = intent.getSerializableExtra("QS") as QuestionsSet
        else
            questionsSet = QuestionsSet()



    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }


    override fun onResume() {
        super.onResume()
        findViewById<EditText>(R.id.create_questions_sets_qs_name).setText(questionsSet?.name)
        Log.d("onCreateView"," edit text")

        val fragment = supportFragmentManager.findFragmentById(R.id.create_questions_sets_question_list_fragment) as QuestionsListFragment
        fragment.setOnClickListener(

            object : QuestionsListAdapter.OnClickListener {
                override fun onItemClick(index: Int) {
                    openQuestionEditor(index)
                }
            })



    }

    override fun onPause() {
        super.onPause()
        val editText = findViewById<EditText>(R.id.create_questions_sets_qs_name)
        questionsSet?.name = editText.text.toString()
    }
    fun getQuestionsSet():QuestionsSet?
    {
        return questionsSet
    }
    fun openCreateQuestionActivity(view: View)
    {
        val intent = Intent(this,QuestionEditorActivity::class.java)//.putExtra("QS", questionsSet)
        startActivityForResult(intent,QUESTION)
    }
    fun openQuestionEditor(index: Int)
    {
        val intent = Intent(this,QuestionEditorActivity::class.java).putExtra("Q", questionsSet?.getQuestion(index))
        startActivityForResult(intent,QUESTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == QUESTION)
        {
            if(resultCode == RESULT_OK)
            {
                val question = data?.getSerializableExtra("Q") as Question
                if(questionsSet!=null &&  question!= null)
                {
                    if(questionsSet?.contains(question)==true)
                        questionsSet?.replace(question)
                    else
                        questionsSet?.addQuestion(question)

                }

            }
        }
    }
    fun saveQuestionsSet(view: View)
    {
        questionsSet?.name = findViewById<EditText>(R.id.create_questions_sets_qs_name).text.toString()
        if (questionsSet?.name=="")
            questionsSet?.name ="default"
        val str = QuizReader.stringOfQuestionsSet(questionsSet ?: return)
        QuizReader.saveStringAsFileInFilesDir(this,questionsSet?.name.toString(), str)
        questionsSet = null
        questionsSet = QuestionsSet()

        finish()
    }

}
