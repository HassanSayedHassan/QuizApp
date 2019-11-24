package com.example.quizapp

import android.net.Uri

class QuestionsSet(var name: String)
{
    val questions: MutableList<Question> = mutableListOf<Question>()
    var image: Uri?=null
    private var progress:QuestionSetProgress = QuestionSetProgress()

    fun addQuestion(question: Question)
    {
        questions.add(question)
    }
}