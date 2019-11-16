package com.example.quizapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class QuestionsSetListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    val arr = arrayOf(QuestionsSet("test1"), QuestionsSet("test2"),QuestionsSet("test3"))

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)

            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }
        }
        catch(e: Exception)
        {
            Log.d("EXCEPTION", e.message)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_questions_set_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = QuestionsSetListAdapter(arr);

        recyclerView = view.findViewById<RecyclerView>(R.id.select_qs_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter

        }



        return view
    }


}
