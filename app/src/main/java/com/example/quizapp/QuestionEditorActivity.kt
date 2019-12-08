package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.util.set
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class QuestionEditorActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var checkBoxArray: SparseBooleanArray = SparseBooleanArray()
    //private val answersList = mutableListOf<String>(
    var question:Question = Question()


    inner class AnswersListAdapter(private val answersList: MutableList<String>): RecyclerView.Adapter<AnswersListAdapter.ViewHolder>()
    {

        inner class ViewHolder(val view: View, val editText: EditText, val checkBox: CheckBox, val editTextListener: EditTextListener): RecyclerView.ViewHolder(view)

        inner class EditTextListener : TextWatcher
        {
            private var position:Int = -1;


            fun updatePosition(position: Int)
            {
                this.position = position
            }
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                answersList[position] = s.toString()
                Log.d("text changed"+position.toString(),s.toString())
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.answers_list_adapter_text_view, parent, false) as TextInputLayout
            val editText = view.findViewById<EditText>(R.id.edit_text)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            return ViewHolder(view, editText, checkBox, EditTextListener())
        }

        override fun onBindViewHolder(holder: AnswersListAdapter.ViewHolder, position: Int) {
            holder.editTextListener.updatePosition(holder.adapterPosition)
            holder.editText.addTextChangedListener(holder.editTextListener)
            holder.editText.setText(answersList[position])
            holder.checkBox.isChecked = checkBoxArray[position]
            holder.checkBox.setOnClickListener() {view -> checkBoxArray[position]=!checkBoxArray[position];Log.d(position.toString(),checkBoxArray[position].toString())}

        }

        override fun getItemCount(): Int {
            return answersList.size
        }


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_editor)

        if(intent.hasExtra("Q")) {
            question = intent.getSerializableExtra("Q") as Question
            checkBoxArray = question.getCorrect()

        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = AnswersListAdapter(question.ans)
        recyclerView = findViewById<RecyclerView>(R.id.answerws_list_recycler_view)
            .apply{setHasFixedSize(true);layoutManager = viewManager; adapter = viewAdapter}


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }

    override fun onResume() {
        super.onResume()
        val editText = findViewById<EditText>(R.id.question_editor_question_content)
        editText.setText(question.content)
    }

    fun createEmptyAnswer(view: View)
    {
        question.addAnswer("")
        viewAdapter.notifyDataSetChanged()
    }

    fun saveQuestion(view: View)
    {

        val content = findViewById<TextInputEditText>(R.id.question_editor_question_content).text.toString()
        question.content = content

        question.setCorrect(checkBoxArray)

        val intent = Intent().putExtra("Q",question)
        setResult(RESULT_OK, intent)
        finish()

    }



}
