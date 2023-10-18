package com.example.mymovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R

class ViewPageAdapter(private var images:List<Int>,private val finishButtonClickListener: FinishButtonClickListener):RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>(){
    inner class Pager2ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image:ImageView=itemView.findViewById(R.id.st_image)
        val finis:Button=itemView.findViewById(R.id.finish)
        val cd:CardView=itemView.findViewById(R.id.card_view)
        val cd2:CardView=itemView.findViewById(R.id.cd_2)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
       val viewHolder=Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider,parent,false))

        return viewHolder
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
        if(position==images.size-1){
           // holder.finis.isEnabled=true
            holder.finis.visibility=View.VISIBLE

            holder.finis.setOnClickListener {
                finishButtonClickListener.onFinishButtonClick()
            }


        }

        else{
            holder.cd.visibility=View.INVISIBLE
            holder.finis.visibility=View.INVISIBLE

            //holder.finis.isEnabled=false

        }
        if(position==0){
            holder.cd2.visibility=View.VISIBLE
        }
        else{
            holder.cd2.visibility=View.INVISIBLE
        }
    }
    interface FinishButtonClickListener {
        fun onFinishButtonClick()
    }


}