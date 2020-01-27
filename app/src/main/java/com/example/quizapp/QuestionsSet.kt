package com.example.quizapp
import android.net.Uri
import java.io.Serializable

class QuestionsSet() :Serializable
{
    var name: String? = null
    private val questions: MutableList<Question> = mutableListOf<Question>()
    private var image: Uri?=null

    constructor(name: String):this()
    {
        this.name = name
    }
    fun addQuestion(question: Question)
    {
        questions.add(question)
    }
    fun getQuestion(index: Int):Question
    {
        return questions[index]
    }
    fun size(): Int
    {
        return questions.size
    }
    fun contains(question: Question): Boolean
    {
        var result = false
        questions.forEach{q -> if (q.content == question.content ) result = true }
        return result
    }
    fun replace(question: Question)
    {
        var index = -1
        for(i in 0..questions.size)
        {
            if(questions[i].content == question.content)
            {
                index = i
                break
            }
        }
        if(index > -1)
        {
            questions.set(index,question)
        }
    }
    fun removeAt(index: Int)
    {
        if(index >= 0 && index < questions.size)
            questions.removeAt(index)
    }
}