package com.example.quizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionsListAdapter(private var questionsSet :QuestionsSet):
    RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    interface OnClickListener
    {
        fun onItemClick(index: Int)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        //val name = view.findViewById<TextView>(R.id.q_list_adapter_name)
        val textView = view.findViewById<TextView>(R.id.q_list_adapter_text)

        fun bindOnClickListener(onClickListener: OnClickListener?, index: Int)
        {
            textView.setOnClickListener{view -> onClickListener?.onItemClick(index)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_list_adapter, parent, false) as View
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.textView.text = questionsSet.getQuestion(position).content
        holder.bindOnClickListener(onClickListener,position)

    }

    override fun getItemCount(): Int {

            return questionsSet.size()
    }

    fun setQuestionsSet(questionsSet: QuestionsSet)
    {
        this.questionsSet=questionsSet
    }

    fun setOnClickListener(onClickListener: OnClickListener)
    {
        this.onClickListener = onClickListener
    }
}