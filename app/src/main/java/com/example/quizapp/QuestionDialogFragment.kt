package com.example.quizapp


import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * A simple [Fragment] subclass.
 */
class QuestionDialogFragment : DialogFragment() {

    companion object {
        const val REMOVE_QUESTION: Int = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val arr = Array<CharSequence>(1) {"Remove"}
            builder.setTitle("Options")
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
            fragment?.startActivityForResult(intent, QuestionsSetDialogFragment.FILE_SAVE_URI)

        }

    }


}
