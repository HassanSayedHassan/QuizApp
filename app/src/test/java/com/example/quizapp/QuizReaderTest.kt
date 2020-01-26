package com.example.quizapp

import org.junit.Test

import org.junit.Assert.*
import com.example.quizapp.QuizReader
import org.junit.Before
import org.junit.BeforeClass

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class QuizReaderTest {

    val JSON_STRING =
        "{\"name\":\"qs1\",\"questions\":[{\"content\":\"pytanie 1\",\"ans\":[\"odpowiedz 1\",\"odpowiedz 2\",\"odpowiedz 3\"],\"correct\":[2]}]}"
    var createdJSON:String=""
    var qs:QuestionsSet? = null
    var qs2:QuestionsSet? = null


    constructor()
    {
        qs = QuestionsSet()

        qs?.name = "qs1"
        val q1 = Question("pytanie 1")
        q1.addAnswer("odpowiedz 1")
        q1.addAnswer("odpowiedz 2")
        q1.addAnswer("odpowiedz 3")
        q1.setCorrect(2)
        qs?.addQuestion(q1)

    }


    @Test
    fun save_isCorrect() {


        createdJSON = QuizReader.stringOfQuestionsSet(qs)

        assertEquals(JSON_STRING,createdJSON)



    }

    @Test
    fun read_isCorrect()
    {
        qs2 = QuizReader.parseJSONString(JSON_STRING)

        assertEquals(qs?.name, qs2?.name)
    }

    @Test
    fun writeRead_isCorrect()
    {

        val qsStr = QuizReader.stringOfQuestionsSet(qs)

        val qsRead = QuizReader.parseJSONString(qsStr)


        assertEquals(qs?.name ,qsRead?.name)
        for (i in 0 until qs!!.size()) {
            assertEquals(qs!!.getQuestion(i).content, qsRead!!.getQuestion(i).content)
            assertEquals(qs!!.getQuestion(i).correct,qsRead!!.getQuestion(i).correct)
            for((a1,a2) in qs!!.getQuestion(i).ans zip qsRead!!.getQuestion(i).ans)
                assertEquals(a1,a2)
        }
    }

}
