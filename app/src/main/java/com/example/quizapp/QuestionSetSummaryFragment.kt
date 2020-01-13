package com.example.quizapp


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.size
import kotlinx.android.synthetic.main.fragment_question_player.*
import kotlinx.android.synthetic.main.fragment_question_set_summary.*
import android.widget.TextView
import android.widget.ArrayAdapter




class QuestionSetSummaryFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_question_set_summary, container, false)

    }
    fun fillList()
    {
        val ac=activity as QuestionsSetPlayerActivity
        val qs:QuestionsSet = activity?.intent?.getSerializableExtra("QS") as QuestionsSet

        var i=0
        val contens=ArrayList<String>()
        while(i<qs.size())
        {
            contens.add(qs.getQuestion(i).content)
            i++
        }
        val adapter =
            object : ArrayAdapter<String>(ac, android.R.layout.simple_list_item_1, contens) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    // Get the Item from ListView
                    val view = super.getView(position, convertView, parent)

                    // Initialize a TextView for ListView each Item
                    val tv = view.findViewById<View>(android.R.id.text1) as TextView
                    tv.textSize=30f
                    tv.setTextColor(Color.WHITE)
                    tv.gravity= Gravity.CENTER
                    tv.setPadding(20,2,20,2)

                   if(ac.wrong_answers.contains(position))
                   {
                       tv.setBackgroundResource(R.drawable.bad_question)
                   }
                   else
                   {
                       tv.setBackgroundResource(R.drawable.good_question)
                   }


                    // Generate ListView Item using TextView
                    return view
                }
            }

        lista.adapter=adapter
        lista.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {
                Log.d("pozycja",position.toString())
                ac.postition=position
                goToReview(view)

            }
        }

    }
    override fun onResume() {
        super.onResume()
        updateData()
        fillList()


    }

    fun goToReview(view:View)
    {

        val ac=activity as QuestionsSetPlayerActivity
        ac.goToReview(view)
    }



    fun updateData()
    {

        val ac=activity as QuestionsSetPlayerActivity
        if(ac.progress!=null) {
            ac.progress?.incrementAttempts()
        }
        result.text="Your score:\n "+ac.score.toString()
        var bests:Int?=ac.progress?.correctAnswers
        if(bests==null)
        {
            bests=0
        }
        if(ac.score>bests)
        {
            ac.progress?.correctAnswers=ac.score
            best.text="New record!\nBest score: \n"+ac.progress?.correctAnswers.toString()
        }
        else
        {
            best.text="Best score:\n"+ac.progress?.correctAnswers.toString()
        }

        attempt.text="Attempt "+ac.progress?.attempts.toString()
        ac.saveToFile()
        ac.score=0

    }
    fun finish(view: View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.finish(view)
    }


}
