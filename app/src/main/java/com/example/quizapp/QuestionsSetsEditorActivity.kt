package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class QuestionsSetsEditorActivity : AppCompatActivity() {

    companion object {
        private const val QUESTION = 100
    }
    private var questionsSet: QuestionsSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_questions_sets_editor)

        val editText = findViewById<EditText>(R.id.create_questions_sets_qs_name)
        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                controlSaveButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

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
        fragment.setOnLongClickListener(
            object : QuestionsListAdapter.OnLongClickListener{
                override fun onItemClick(index: Int) {

                    if(questionsSet!=null) {
                        val builder = AlertDialog.Builder(this@QuestionsSetsEditorActivity)
                        builder.setTitle("Options")
                            .setMessage("Remove question?")
                            .setPositiveButton("Yes"){dialog, which ->
                                removeQuestion(index)
                                fragment.updateDataSet(questionsSet)
                                controlSaveButtonState()
                            }
                        builder.setNegativeButton("No"){_,_ -> }

                        val dialog = builder.create()
                        dialog.show()

                    }
                }
            }
        )




    }

    override fun onPause() {
        super.onPause()
        val editText = findViewById<EditText>(R.id.create_questions_sets_qs_name)
        questionsSet?.name = editText.text.toString()
    }

    private fun isTitleSet():Boolean
    {
        val editText = findViewById<EditText>(R.id.create_questions_sets_qs_name)

        return !editText.text.isEmpty()

    }

    private fun isAnyQuestionAdded():Boolean
    {
        return compareValues(questionsSet?.size(),0) > 0
    }

    private fun isReadyToSave():Boolean{
        return isTitleSet() && isAnyQuestionAdded()
    }

    private fun controlSaveButtonState()
    {
        val button8 = findViewById<Button>(R.id.button8)
        button8.isEnabled = isReadyToSave()
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

    private fun removeQuestion(index: Int)
    {
        questionsSet?.removeAt(index)
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
