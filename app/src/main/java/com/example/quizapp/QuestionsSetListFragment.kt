package com.example.quizapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.zip.CheckedOutputStream


class QuestionsSetListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: QuestionsSetListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val SELECT_FILE = 100
    private var selectedFile: Uri?  = null
    private lateinit var questionDatabase:ArrayList<QuestionsSet>
    private val quizReader = QuizReader()


    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)

        if(this.id == R.id.questions_sets_fragment)
        setHasOptionsMenu(true)
        questionDatabase = quizReader.readSets(activity as Activity)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        Log.d("ID:", this.id.toString())
        if(this.id == R.id.questions_sets_fragment) {
            inflater.inflate(R.menu.questions_sets_menu,menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId) {
            R.id.add_qs -> {addQuestionsSet(); return true}
            R.id.create_qs -> {createQuestionsSet(); return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {

            selectedFile = data?.data


            val string = quizReader.readFile(activity as Context, selectedFile)
            if(string != null) {
                quizReader.saveStringAsFileInFilesDir(activity as Context, string)

            }
            updateQuestionDatabase()

        }
        Log.d("URI: ",selectedFile.toString())


    }

    private fun updateQuestionDatabase()
    {
        questionDatabase.clear()
        questionDatabase = quizReader.readSets(activity as Activity)
        viewAdapter.setDataSet(questionDatabase)
        viewAdapter.notifyDataSetChanged()
    }

    private fun createQuestionsSet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun addQuestionsSet() {
        val intent = Intent().setAction(Intent.ACTION_OPEN_DOCUMENT).setType("*/*")
        startActivityForResult(intent,SELECT_FILE)
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









}
