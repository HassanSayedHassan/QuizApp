package com.example.quizapp


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class QuestionsSetDialogFragment : DialogFragment() {


    companion object {
        const val FILE_SAVE_URI: Int = 111
    }
    private var questionsSet:QuestionsSet? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_questions_set_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val arr = Array<CharSequence>(2) {i -> if (i ==0) "Eksportuj" else "Usuń"}
            builder.setTitle("Opcje")
            builder.setItems(arr){dialog, which -> dialogAction(which)}
            builder.create()


        }?:throw IllegalStateException()

    }

    fun dialogAction(which: Int)
    {
        if(which == 0)
        {
            val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.questions_sets_fragment)
            val intent = Intent().setAction(Intent.ACTION_CREATE_DOCUMENT).setType("*/*")
            fragment?.startActivityForResult(intent, FILE_SAVE_URI)

        }
        else if(which == 1)
        {
            val rvFragment:QuestionsSetListFragment = activity?.supportFragmentManager?.findFragmentById(R.id.questions_sets_fragment) as QuestionsSetListFragment
            rvFragment.deleteQuestionsSet()
        }
    }



    fun setQuestionsSet(questionsSet: QuestionsSet)
    {
        this.questionsSet = questionsSet
    }
}
