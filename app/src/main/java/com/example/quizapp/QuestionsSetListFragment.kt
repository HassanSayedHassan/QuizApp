package com.example.quizapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class QuestionsSetListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null
    private val files = activity?.fileList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    val arr = arrayOf(QuestionsSet("test1"), QuestionsSet("test2"),QuestionsSet("test3"))

    val questionDatabase =
        arrayOf(QuestionsSet("zestaw1"),
            QuestionsSet("zestaw2"),
            QuestionsSet("Zestaw3"),
            QuestionsSet("zestaw4"),
            QuestionsSet("zestaw5"),
            QuestionsSet("zestaw6")
        )

    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)

            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }

        questionDatabase[0].addQuestion(Question("q1"))
        questionDatabase[0].addQuestion(Question("q2"))
        questionDatabase[0].addQuestion(Question("q3"))
        questionDatabase[0].addQuestion(Question("q4"))
        questionDatabase[0].addQuestion(Question("q5"))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_questions_set_list, container, false)
        viewManager = LinearLayoutManager(context)
        viewAdapter = QuestionsSetListAdapter(questionDatabase)
        recyclerView = view.findViewById<RecyclerView>(R.id.questions_sets_recycler).apply {
            layoutManager = viewManager
            adapter = viewAdapter

        }

        return view
    }

    fun setOnClickListener(onClickListener: QuestionsSetListAdapter.OnClickListener)
    {
       val adapter = viewAdapter as QuestionsSetListAdapter
        adapter.setOnClickListener(onClickListener)
    }

    fun setOnLongClickListener(onLongClickListener: QuestionsSetListAdapter.OnLongClickListener)
    {
        val adapter = viewAdapter as QuestionsSetListAdapter
        adapter.setOnLongClickListener(onLongClickListener)
    }

    fun readSets(view: View):Array<QuestionsSet?>
    {
        var n=0
        if(files?.size!=null)
        {
            n=files?.size
        }
        val result= arrayOfNulls<QuestionsSet>(n)
        var i=0
        while(i<n)
        {
            var name=""
            if(files?.get(i)!=null)
            {
            name=files[i]
            }
            val file = File(activity?.filesDir, name)

            val jsonString:String= file.readText(Charsets.UTF_8)
            val reader:QuizReader= QuizReader()
            val newSet:QuestionsSet=reader.read(jsonString)
            result[i]=newSet
            i++
        }

        return result
    }



}
