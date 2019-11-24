package com.example.quizapp
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

class QuizReader
{

    fun read(json: String):QuestionsSet
    {

        val jsonobj=JSONObject(json)
        val set=jsonobj.getJSONObject("Quiz")
        val name=set.getString("Category")
        val result = QuestionsSet(name)
        val jsonlist = set.getJSONArray("Questions")


        var i=0

        while(i<jsonlist.length())
        {

            val obj=jsonlist.getJSONObject(i)
            val question = Question(obj.getString("textcontent"))

            val answers=obj.getJSONArray("answers")
            var j=0
            while(j<answers.length())
            {
                val answer=answers.getString(j)
                question.ans.add(answer)
                print(answer)
                j++
            }
            val correct=obj.getJSONArray("correct")
            var k=0
            while(k<correct.length())
            {
                val corr=correct.getInt(k)
                print(corr)
                question.correct.add(corr)
                k++
            }
            result.questions.add(question)
            i++

        }


        return result
    }
}