package com.example.quizapp

import java.io.Serializable

class QuestionSetProgress: Serializable {
    var correctAnswers: Int=0
    var attempts: Int=0
    public var questionsSetName: String?="";
}