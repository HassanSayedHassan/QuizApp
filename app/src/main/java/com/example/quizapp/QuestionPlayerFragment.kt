package com.example.quizapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_question_player.*
import android.widget.Toast
import android.R.interpolator.linear
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.view.marginBottom


/**
 * A simple [Fragment] subclass.
 */
class QuestionPlayerFragment : Fragment() {

    val answers_checked:ArrayList<Int> = ArrayList<Int>()
    var last:Boolean=false
    companion object
    {
        var index:Int=0
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_player, container, false)
    }
    fun incIndex()
    {
        index++
    }


    override fun onResume() {
        super.onResume()
        updateQuestionData(index)
    }
    fun updateQuestionData(i:Int)
    {
        answers_checked.clear()
        next_button.text="Check"
        next_button.setOnClickListener(View.OnClickListener { view ->
            check(view)})
        q_container.removeAllViews()


        val qs:QuestionsSet = activity?.intent?.getSerializableExtra("QS") as QuestionsSet

        if(i>=qs.size())
        {

                next_button.text="Zakończ quiz"
                next_button.setOnClickListener(View.OnClickListener { view ->
                    goToSummary(view)})

        }
        else
        {
            if(i>=qs.size()-1)
            {
                last=true


            }
            val ac=activity as QuestionsSetPlayerActivity

            val q:Question?=qs?.getQuestion(i)
            val q_content=TextView(ac)
            q_content.textSize=30f
            q_content.setTextColor(Color.WHITE)
            q_content.gravity=Gravity.CENTER

            q_content.setPadding(20,20,20,20)
            val textLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 350)
            textLayoutParams.setMargins(60,60,60,60)
            q_container.addView(q_content,textLayoutParams)
            var ans_size=q?.ans?.size
            if(ans_size==null)
            {
                ans_size=0
            }
            q_content.text=((i+1).toString()+". "+q?.content)
            for (i in 0..ans_size-1) {

                var btn = Button(ac)
                btn.setId(i)
                val id_ = btn.getId()
                btn.setText(q?.ans?.get(i))
                btn.height=65
                btn.setBackgroundResource(R.drawable.answer_button)
                btn.setTextColor(Color.WHITE)
                btn.textSize=17f
                val buttonLayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                buttonLayoutParams.setMargins(30, 20, 30, 10);

                q_container.addView(btn, buttonLayoutParams)
                btn = ac.findViewById(id_) as Button
                btn.setOnClickListener(View.OnClickListener { view ->
                    if(!answers_checked.contains(i))
                    {
                        answers_checked.add(i)
                        btn.setBackgroundResource(R.drawable.checked_answer)
                        /*Toast.makeText(
                            view.context,
                            "Button clicked index = $id_", Toast.LENGTH_SHORT

                        )
                            .show()*/
                    }
                    else
                    {

                        answers_checked.remove(i)
                        btn.setBackgroundResource(R.drawable.answer_button)

                    }

                })
            }
        }


    }
    fun changeCorrectButtons()
    {

        answers_checked.sort()
        val ac=activity as QuestionsSetPlayerActivity
        var all_good:Boolean=true
        val qs:QuestionsSet = activity?.intent?.getSerializableExtra("QS") as QuestionsSet
        val q:Question?=qs?.getQuestion(index)
        var cor_size=q?.correct?.size
        if(cor_size==null)
        {
            cor_size=0
        }

        var i=0
        while(i<cor_size)
        {

            var correct_button:Int?=q?.correct?.get(i)
            if(correct_button==null)
            {
                correct_button=0
            }
            var good_btn = ac.findViewById(correct_button) as Button
            if(answers_checked.contains(correct_button))
            {

                good_btn.setBackgroundResource(R.drawable.correct_answer)
            }
            else
            {
                good_btn.setBackgroundResource(R.drawable.correct_answer)
            }

//            if(answers_checked.get(i)!=q?.correct?.get(i))
//            {
//                all_good=false
//            }
            i++
        }
        if(answers_checked.size==cor_size)
        {
            var j=0
            while(j<cor_size)
            {

                if(!answers_checked.contains(q?.correct?.get(j)))
                {
                    all_good=false
                }
                j++
            }

        }
        else
        {
            all_good=false
        }
        if(all_good)
        {


            ac.score= ac.score+1


        }
        else
        {
            ac.writeWrongAnswer(index)
        }
    }
    fun changeButtons()
    {

        answers_checked.sort()
        val ac=activity as QuestionsSetPlayerActivity
        var all_good:Boolean=true
        val qs:QuestionsSet = activity?.intent?.getSerializableExtra("QS") as QuestionsSet
        val q:Question?=qs?.getQuestion(index)
        var cor_size=q?.correct?.size
        if(cor_size==null)
        {
            cor_size=0
        }

        var i=0
        while(i<answers_checked.size)
        {
            var checked_button:Int=answers_checked.get(i)
            var wrong_btn = ac.findViewById(checked_button) as Button

            wrong_btn.setBackgroundResource(R.drawable.wrong_answer)
            i++
        }
        changeCorrectButtons()
        if(last)
        {
            index=0
            next_button.text="Finish Quiz!"
            next_button.setOnClickListener(View.OnClickListener { view ->
                goToSummary(view)})
            answers_checked.clear()
        }
        else
        {
            next_button.text="Next question"
            next_button.setOnClickListener(View.OnClickListener { view ->
                goNext(view)})
        }


    }
    fun check(view: View)
    {

        val ac=activity as QuestionsSetPlayerActivity
        ac.check(view)

    }
    fun goNext(view: View)
    {

        val ac=activity as QuestionsSetPlayerActivity
        ac.goNext(view)
    }
    fun goToSummary(view: View)
    {

        val ac=activity as QuestionsSetPlayerActivity
        ac.goToSummary(view)
    }


}
