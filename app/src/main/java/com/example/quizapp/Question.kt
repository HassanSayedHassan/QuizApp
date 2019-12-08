package com.example.quizapp

import android.util.Log
import android.util.SparseBooleanArray
import androidx.core.util.forEach
import java.io.Serializable

open class Question(): Serializable
{
    constructor(content: String): this()
    {
        this.content = content
    }
    var content:String = ""
    var ans: MutableList<String> = mutableListOf<String>()
    var correct: MutableList<Int> = mutableListOf()

    fun addAnswer(answer: String)
    {
        ans.add(answer)
    }
    fun setAnswer(answer: String, position:Int)
    {
        if(position >= 0 && position < ans.size)
        {
            ans[position] = answer
        }
    }
    fun setCorrect(answerArray: SparseBooleanArray)
    {
        answerArray.forEach({k:Int,v:Boolean->if(v == true) correct.add(k)})
        Log.d("correct: ", correct.size.toString())
    }
    fun getCorrect():SparseBooleanArray
    {
        val array = SparseBooleanArray()
        correct.forEach{i -> array.put(i,true)}
        return array
    }





}