package com.example.quizapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionsSetListAdapter(private val questionsSetList :Array<QuestionsSet>):
    RecyclerView.Adapter<QuestionsSetListAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsSetListAdapter.ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.questions_set_list_adapter_text_view, parent, false) as TextView
        return  ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return  questionsSetList.size
    }

}