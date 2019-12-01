package com.example.quizapp

import android.content.Context
import android.util.Log
import com.google.gson.*
import java.io.File


class QuizBuilder
{
    var qs:QuestionsSet= QuestionsSet()
    val answers: MutableList<String> = mutableListOf<String>()
    val corrects: MutableList<Int> = mutableListOf<Int>()



    fun createSet(name:String)
    {
        qs= QuestionsSet(name)


    }

    fun createQuestion(content: String):Question
    {
        val q= Question(content)

        q.ans = answers.toMutableList()
        q.correct = corrects.toMutableList()
        qs.addQuestion(q)
        answers.clear()
        corrects.clear()
        return q

    }
    fun addAnswer(answer: String, isCorrect: Boolean)
    {
        answers.add(answer)
        val index=answers.indexOf(answer)
        if(isCorrect)
        {
           corrects.add(index)
        }
    }
    fun saveToFile(context: Context)
    {
        val gson = GsonBuilder().setPrettyPrinting().create()

        val setToSave = qs
        val jsonSet: String = gson.toJson(setToSave)
        val file_count = context.filesDir?.listFiles()?.size
        val file = File(context.filesDir,"file_"+file_count)
        file.writeText(jsonSet)
        Log.d("file path", file.absolutePath)

    }

}