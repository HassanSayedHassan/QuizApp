package com.example.quizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


import android.view.View
import androidx.fragment.app.Fragment
import com.example.quizapp.QuestionPlayerFragment.Companion.index
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream



class QuestionsSetPlayerActivity : AppCompatActivity() {

    var progress_list:ArrayList<QuestionSetProgress?> = ArrayList()
    val fragment =  QuestionsSetProgressFragment()
    var q_fragment = QuestionPlayerFragment()
    var s_fragment=QuestionSetSummaryFragment()
    var progress:QuestionSetProgress?=QuestionSetProgress()
    var score:Int=0


    companion object {
        val gson = GsonBuilder().create()
        val PROGRESS_DIR_NAME = "Progress"
        val PROGRESS_FILE_NAME = "progress.json"

        fun parseListToJson(list:ArrayList<QuestionSetProgress?>):String
        {
            return gson.toJson(list)
        }

        fun parseJSONString(json: String?): ArrayList<QuestionSetProgress?> {
            if(json!="")
            {
                val listType = object : TypeToken<List<QuestionSetProgress?>>() {

                }.type
                return gson.fromJson(json,listType)
            }
            else
            {

                return ArrayList<QuestionSetProgress?>()
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_set_player)
        progress_list = readProgress(this)
        /*
        var filename = "progress"

        val Directory = File(filesDir,"Progress")

        Directory.mkdirs()

        val outputFile = File(Directory, filename)

        try {
            val fos = FileOutputStream(outputFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val directory = File(filesDir, PROGRESS_DIR_NAME)
        val filesList = directory.listFiles().filter { file -> file.name == PROGRESS_FILE_NAME }
        if(filesList.size > 0) {




        }*/
        showProgress(fragment)


    }
    fun saveToFile()
    {
        progress_list.add(progress)
        val json_list= parseListToJson(progress_list)
        val directory = File(filesDir, PROGRESS_DIR_NAME)
        if(!directory.exists())
            directory.mkdir()

        //var filename = "progress"
        val outputFile = File(directory, PROGRESS_FILE_NAME)
        outputFile.writeText(json_list)

        /*
        try {
            //val fos = FileOutputStream(outputFile)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        */

    }
    override fun onResume() {
        super.onResume()
        progress=getProgress(this)
        //Log.d("progress name",progress?.questionsSetName)
    }

    fun update()
    {
        var qs:QuestionsSet = intent.getSerializableExtra("QS") as QuestionsSet
        fragment.updateTextDataFromProgress(qs,this)
    }
    fun finish(view: View)
    {
        saveToFile()
        finish()

        //val intent=Intent(this,MainMenuActivity::class.java)
        //startActivity(intent)
    }
    fun goToSummary(view: View)
    {

        val transaction = supportFragmentManager.beginTransaction()



        transaction.replace(R.id.container ,s_fragment)
        transaction.commit()

    }

    fun goToPlayer(view: View)
    {

        val transaction = supportFragmentManager.beginTransaction()



        transaction.replace(R.id.container ,q_fragment)
        transaction.commit()

    }

    fun goNext(view: View)
    {


        q_fragment.incIndex()



        q_fragment.updateQuestionData(index)

    }
    fun check(view:View)
    {

        q_fragment.changeButtons()
    }
    fun showProgress(fragment:Fragment)
    {

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.container ,fragment)

        transaction.commit()




    }
    fun getProgress(context:QuestionsSetPlayerActivity):QuestionSetProgress?
    {
        var qs:QuestionsSet = intent.getSerializableExtra("QS") as QuestionsSet
        return progress_list.find { progress -> progress?.questionsSetName.equals(qs.name) }

        /*
        Log.d("getProgress:","method")
        val list:ArrayList<QuestionSetProgress?> = readProgress(context)
        var i=0
        while(i<list.size)
        {
            Log.d("while loop","in")
            var qs:QuestionsSet = intent.getSerializableExtra("QS") as QuestionsSet
            Log.d("qs",qs.name)
            //Log.d("qs_name",list[i]?.questionsSetName)
            if(list[i]?.questionsSetName.equals(qs.name))
            {
                Log.d("progress: ","found")
                return list[i]

            }
            i++
        }
        return null*/

    }

    fun readProgress(context:QuestionsSetPlayerActivity):ArrayList<QuestionSetProgress?>
    {

        var result :ArrayList<QuestionSetProgress?> = ArrayList()



        val directory = File(filesDir, PROGRESS_DIR_NAME)
        if(directory.exists()) {
            val filesList = directory.listFiles().filter { file -> file.name == PROGRESS_FILE_NAME }
            if (filesList.isNotEmpty()) {

                val jsonString: String? = filesList[0].readText(Charsets.UTF_8)
                result = parseJSONString(jsonString)
                Log.d("resultSize: ", result.size.toString())

            }
        }
        /*
        val files = context.filesDir.listFiles()
        var i=0
        var size:Int?=files?.size
        if(size==null)
        {
            size=0
        }
        while(i<size)
        {
            if(files[i].name=="progress")
            {

                val file=files[i]
                val jsonString: String? = file?.readText(Charsets.UTF_8)


                val result = parseJSONString(jsonString)
                return result
            }
            i++
        }*/
        return result
    }








}
