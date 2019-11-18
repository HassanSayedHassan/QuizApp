package com.example.quizapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuestionsSetListAdapter(private val questionsSetList :Array<QuestionsSet>):
    RecyclerView.Adapter<QuestionsSetListAdapter.ViewHolder>() {



    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null

    interface OnClickListener
    {
        fun onItemClick(index: Int)
    }
    interface OnLongClickListener
    {
        fun onItemClick(index: Int)
    }

    class ViewHolder(val view: CardView): RecyclerView.ViewHolder(view)
    {
        val textView = view.findViewById<TextView>(R.id.qs_list_adapter_text)
        val imageView = view.findViewById<ImageView>(R.id.qs_list_adapter_image)

        fun bindOnClickListener(onClickListener: OnClickListener?, index: Int)
        {
            if(onClickListener != null)
            view.setOnClickListener { view -> onClickListener.onItemClick(index) }
        }
        fun binOnLongClikckListener(onLongClickListener: OnLongClickListener?, index: Int)
        {
            if (onLongClickListener != null)
            view.setOnLongClickListener { view -> onLongClickListener.onItemClick(index); true }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.questions_set_list_adapter_card_view, parent, false) as CardView
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = questionsSetList[position].name
        holder.imageView.setImageResource(R.drawable.b_image)
        holder.bindOnClickListener(onClickListener,position)
        holder.binOnLongClikckListener(onLongClickListener,position)

    }

    override fun getItemCount(): Int {
        return  questionsSetList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener)
    {
        this.onClickListener = onClickListener
    }
    fun setOnLongClickListener(onLongClickListener: OnLongClickListener)
    {
        this.onLongClickListener = onLongClickListener
    }


}