package com.example.quizapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_question_set_summary.*


class QuestionSetSummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_question_set_summary, container, false)
    }
    override fun onResume() {
        super.onResume()
        updateData()
    }
    fun updateData()
    {

        val ac=activity as QuestionsSetPlayerActivity
        if(ac.progress!=null) {
            ac.progress?.incrementAttempts()
        }
        result.text="Twój wynik:\n "+ac.score.toString()
        var bests:Int?=ac.progress?.correctAnswers
        if(bests==null)
        {
            bests=0
        }
        if(ac.score>bests)
        {
            ac.progress?.correctAnswers=ac.score
            best.text="Nowy rekord!\nNajlepszy wynik: \n"+ac.progress?.correctAnswers.toString()
        }
        else
        {
            best.text="Najlepszy wynik:\n"+ac.progress?.correctAnswers.toString()
        }

        attempt.text="Próba numer "+ac.progress?.attempts.toString()
        ac.saveToFile()
        ac.score=0

    }
    fun finish(view: View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.finish(view)
    }


}
