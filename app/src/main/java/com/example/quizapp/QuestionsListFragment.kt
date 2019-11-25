package com.example.quizapp


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestionsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var questionsSet:QuestionsSet = QuestionsSet()
    private val quizReader = QuizReader()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = activity?.intent?.getIntExtra("index",-1)
        if(index != null && index > -1)
        {
            questionsSet = quizReader.readSets(activity as Activity)[index]
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {






        val view = inflater.inflate(R.layout.fragment_questions_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = QuestionsListAdapter(questionsSet)

        recyclerView = view.findViewById<RecyclerView>(R.id.questions_list_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter}

        return view
    }


}
