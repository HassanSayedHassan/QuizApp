package com.example.quizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


import android.view.View
import androidx.fragment.app.Fragment
import com.example.quizapp.QuestionPlayerFragment.Companion.index
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream



class QuestionsSetPlayerActivity : AppCompatActivity() {

    val progress_list:ArrayList<QuestionSetProgress?> = ArrayList()
    val fragment =  QuestionsSetProgressFragment()
    var q_fragment = QuestionPlayerFragment()
    var s_fragment=QuestionSetSummaryFragment()
    var progress:QuestionSetProgress?=QuestionSetProgress()
    var score:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_set_player)

        var filename = "progress"

        val Directory = File(filesDir,"Progress")

        Directory.mkdirs()

        val outputFile = File(Directory, filename)

        try {
            val fos = FileOutputStream(outputFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }


        showProgress(fragment)




    }
    fun saveToFile()
    {
        progress_list.add(progress)
        val json_list= parseListToJson(progress_list)
        var filename = "progress"

        val outputFile = File("Progress", filename)


        try {
            val fos = FileOutputStream(outputFile)
            outputFile.writeText(json_list)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
    override fun onResume() {
        super.onResume()
        progress=getProgress(this)
    }
    companion object {
        val gson = GsonBuilder().create()
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
    fun update()
    {
        var qs:QuestionsSet = intent.getSerializableExtra("QS") as QuestionsSet
        fragment.updateTextDataFromProgress(qs,this)
    }
    fun finish(view: View)
    {
        val intent=Intent(this,MainMenuActivity::class.java)
        startActivity(intent)
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

        val list:ArrayList<QuestionSetProgress?> = readProgress(context)
        var i=0
        while(i<list.size)
        {
            var qs:QuestionsSet = intent.getSerializableExtra("QS") as QuestionsSet

            if(list[i]?.questionsSetName.equals(qs.name))
            {
                return list[i]
            }
            i++
        }
        return null

    }

    fun readProgress(context:QuestionsSetPlayerActivity):ArrayList<QuestionSetProgress?>
    {

        var result :ArrayList<QuestionSetProgress?> = ArrayList()
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
        }
        return result
    }








}
