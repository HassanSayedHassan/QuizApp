package com.example.quizapp

import android.net.Uri

class QuestionsSet()
{
    var name: String? = null
    private val questions: MutableList<Question> = mutableListOf<Question>()
    private var image: Uri?=null
    private var progress:QuestionSetProgress = QuestionSetProgress()

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
}