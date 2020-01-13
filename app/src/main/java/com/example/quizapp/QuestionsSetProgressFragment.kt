package com.example.quizapp


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_questions_set_progress.view.*
import kotlinx.android.synthetic.main.activity_questions_set_player.*
import kotlinx.android.synthetic.main.fragment_questions_set_progress.*


class QuestionsSetProgressFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_questions_set_progress, container, false)

    }

    override fun onResume() {
        super.onResume()
        val ac=activity as QuestionsSetPlayerActivity
        ac.update()
    }




    fun updateTextDataFromProgress(qs:QuestionsSet,context:QuestionsSetPlayerActivity)
    {

        val ac=activity as QuestionsSetPlayerActivity
        Log.d("as","updatuje")
        //Toast.makeText(context,"asdas",Toast.LENGTH_SHORT)
        var progress=ac.progress
        if(progress==null)
        {
            Log.d("","byl null")
            progress= QuestionSetProgress()
            val name:String?=qs.name
            Log.d("nazwa",name)
            progress?.questionsSetName=name
            progress?.attempts=0
            progress?.correctAnswers=0

            ac.progress=progress

        }

        Log.d("progress_qs_name",progress?.questionsSetName)

        category_tv.text=("Quiz: "+progress.questionsSetName)
        Log.d("w cat",category_tv.getText().toString())

        if(progress?.attempts==0)
        {
            attempts_tv.text="First attempt"

        }
        else
        {

            best_tv.setText("Best Score: "+progress?.correctAnswers)

            val incrementedAttempts = progress.attempts + 1
            attempts_tv.setText("Attempt: "+ incrementedAttempts)
        }


    }


    fun goToPlayer(view: View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.goToPlayer(view)
    }








}