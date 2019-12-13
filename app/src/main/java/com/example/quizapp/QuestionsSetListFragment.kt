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
import com.google.android.material.floatingactionbutton.FloatingActionButton


class QuestionsSetListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: QuestionsSetListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var selectedFile: Uri?  = null
    private lateinit var questionDatabase:ArrayList<QuestionsSet>
    var selectedQuestionsSet = Int.MIN_VALUE

    companion object
    {
        private const val SELECT_FILE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)

        if(this.id == R.id.questions_sets_fragment)
        setHasOptionsMenu(true)
        questionDatabase = QuizReader.readSets(activity as Activity)

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

    override fun onResume() {
        super.onResume()
        updateQuestionDatabase()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setFABOnClickListener { view -> createQuestionsSet() }
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


            val string = QuizReader.readFile(activity as Context, selectedFile)
            if(string != null) {
                QuizReader.saveStringAsFileInFilesDir(activity as Context, QuizReader.getNameFromString(string)?:return,string)

            }
            updateQuestionDatabase()

        }
        else if(requestCode == QuestionsSetDialogFragment.FILE_SAVE_URI && resultCode == RESULT_OK) {
            val uri = data?.data
            if(selectedQuestionsSet != Int.MIN_VALUE) {
                val str = QuizReader.stringOfQuestionsSet(questionDatabase.get(selectedQuestionsSet))
                Log.d("str", str)
                Log.d("uri", uri.toString())
                QuizReader.writeStringToUri(activity as Context, str, uri)
            }

        }
    }

    private fun updateQuestionDatabase()
    {
        questionDatabase.clear()
        questionDatabase = QuizReader.readSets(activity as Activity)
        viewAdapter.setDataSet(questionDatabase)
        viewAdapter.notifyDataSetChanged()
    }

    private fun createQuestionsSet() {
        val intent = Intent(activity,QuestionsSetsEditorActivity::class.java)
        startActivity(intent)

    }

    private fun addQuestionsSet() {
        val intent = Intent().setAction(Intent.ACTION_OPEN_DOCUMENT).setType("*/*")
        startActivityForResult(intent,SELECT_FILE)
    }

    fun setOnElementClickListener(onClickListener: QuestionsSetListAdapter.OnClickListener)
    {
       val adapter = viewAdapter as QuestionsSetListAdapter
        adapter.setOnClickListener(onClickListener)
    }

    fun setOnLongElementClickListener(onLongClickListener: QuestionsSetListAdapter.OnLongClickListener)
    {
        val adapter = viewAdapter as QuestionsSetListAdapter
        adapter.setOnLongClickListener(onLongClickListener)
    }
    fun setFABOnClickListener(onClickListener: ((v:View)->Unit)?)
    {

        val fab = activity?.findViewById<FloatingActionButton>(R.id.questions_sets_fragment_fab)
        fab?.setOnClickListener(onClickListener)
    }
    fun getQuestionsSet(index: Int):QuestionsSet
    {
        return questionDatabase[index]
    }
    fun deleteQuestionsSet()
    {
        if(selectedQuestionsSet!=Int.MIN_VALUE )
        {
            QuizReader.deleteQuestionsSet(activity as Activity,questionDatabase[selectedQuestionsSet].name)
            updateQuestionDatabase()
        }
    }










}
