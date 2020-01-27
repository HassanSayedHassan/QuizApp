package com.example.quizapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestionsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: QuestionsListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var questionsSet: QuestionsSet = QuestionsSet()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index = activity?.intent?.getIntExtra("index", -1)

        if (index != null && index > -1) {
            questionsSet = QuizReader.readSets(activity as Activity)[index]

        } else if (activity is QuestionsSetsEditorActivity) {

            val qs = (activity as QuestionsSetsEditorActivity).getQuestionsSet()
            if (qs != null) {

                questionsSet = qs

            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_questions_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = QuestionsListAdapter(questionsSet)

        recyclerView = view.findViewById<RecyclerView>(R.id.questions_list_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        if (activity is QuestionsSetsEditorActivity) {

            val qs = (activity as QuestionsSetsEditorActivity).getQuestionsSet()
            if (qs != null) {
                questionsSet = qs
                updateDataSet(questionsSet)
                Log.d("onResume", qs.size().toString())
            }


        }

    }

    fun updateDataSet(questionsSet: QuestionsSet?) {
        viewAdapter.setQuestionsSet(questionsSet)
        viewAdapter.notifyDataSetChanged()
    }

    fun setOnClickListener(onClickListener: QuestionsListAdapter.OnClickListener)
    {
        val adapter = viewAdapter
        adapter.setOnClickListener(onClickListener)
    }

    fun setOnLongClickListener(onLongClickListener: QuestionsListAdapter.OnLongClickListener)
    {
        viewAdapter.setOnLongClickListener(onLongClickListener)
    }


}
