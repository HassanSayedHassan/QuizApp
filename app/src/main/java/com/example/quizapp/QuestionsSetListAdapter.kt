package com.example.quizapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuestionsSetListAdapter(private val questionsSetList :Array<QuestionsSet>):
    RecyclerView.Adapter<QuestionsSetListAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView): RecyclerView.ViewHolder(cardView)
    {
        val textView = cardView.findViewById<TextView>(R.id.qs_list_adapter_text)
        val imageView = cardView.findViewById<ImageView>(R.id.qs_list_adapter_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsSetListAdapter.ViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.questions_set_list_adapter_card_view, parent, false) as CardView
        return  ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = questionsSetList[position].name
        holder.imageView.setImageResource(R.drawable.b_image)

    }

    override fun getItemCount(): Int {
        return  questionsSetList.size
    }

}