package com.example.quizapp

import android.net.Uri

class QuestionsSet(var name: String)
{
    var questions: MutableList<Question> = mutableListOf<Question>()
    var image: Uri?=null
    var progress:QuestionSetProgress = QuestionSetProgress()
}