package com.example.quizapp


import android.os.Bundle
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


    private val arr = arrayOf(Question("pytanie1"),
        Question("pytanie2"),
        Question("pytanie3"),
        Question("pytanie4"),
        Question("pytanie5"),
        Question("pytanie6"),
        Question("pytanie7"))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_questions_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = QuestionsListAdapter(arr)

        recyclerView = view.findViewById<RecyclerView>(R.id.questions_list_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter}

        return view
    }


}
