package com.example.quizapp

import java.io.Serializable

class QuestionSetProgress: Serializable {
    var correctAnswers: Int=0
    var attempts: Int=0
    var questionsSetName: String?="";

    fun incrementAttempts()
    {
        attempts += 1
    }
}