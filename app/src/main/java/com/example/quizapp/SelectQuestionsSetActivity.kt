package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectQuestionsSetActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_questions_set)

        viewManager = LinearLayoutManager(this)

        //viewAdapter = QuestionsSetListAdapter(QuestionsSetList)
        recyclerView = findViewById<RecyclerView>(R.id.select_qs_recycler).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            //adapter = viewAdapter
        }
    }
}
