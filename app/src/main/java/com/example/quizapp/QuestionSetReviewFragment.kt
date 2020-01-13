package com.example.quizapp


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_question_set_review.*

/**
 * A simple [Fragment] subclass.
 */
class QuestionSetReviewFragment : Fragment() {



    val fragment=QuestionSetSummaryFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_set_review, container, false)
    }

    override fun onResume() {
        super.onResume()
        val ac=activity as QuestionsSetPlayerActivity
        updateWrong(ac.postition)
    }
    fun updateWrong(n:Int)
    {
        Log.d("pytanie",n.toString())
        wrong_container.removeAllViews()
        val ac=activity as QuestionsSetPlayerActivity

        val qs:QuestionsSet = activity?.intent?.getSerializableExtra("QS") as QuestionsSet


        val q:Question?=qs?.getQuestion(n)

        val q_content=TextView(ac)
        q_content.textSize=30f
        q_content.setTextColor(Color.WHITE)
        q_content.gravity= Gravity.CENTER

        q_content.setPadding(20,20,20,20)
        val textLayoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 350)
        textLayoutParams.setMargins(60,60,60,60)
        wrong_container.addView(q_content,textLayoutParams)
        var ans_size=q?.ans?.size
        if(ans_size==null)
        {
            ans_size=0
        }
        q_content.text=((n+1).toString()+". "+q?.content)
        for (i in 0..ans_size-1) {

            var btn = Button(ac)
            btn.setId(i)
            val id_ = btn.getId()
            btn.setText(q?.ans?.get(i))
            btn.setBackgroundColor(Color.rgb(100, 20, 255))
            btn.setTextColor(Color.WHITE)
            btn.textSize=17f
            val buttonLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            buttonLayoutParams.setMargins(30, 20, 30, 10)


            if(q?.correct!=null && q?.correct.contains(i))
            {
                wrong_container.addView(btn, buttonLayoutParams)
                btn.setBackgroundResource(R.drawable.correct_answer)
            }

        }

    }
    fun nextWrong(view:View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.nextWrong(view)

    }
    fun previousWrong(view: View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.previousWrong(view)

    }
    fun goToSummary(view: View)
    {
        val ac=activity as QuestionsSetPlayerActivity
        ac.goToSummary(view)
    }


}
