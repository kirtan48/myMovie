package com.example.mymovies.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.room.Movie
import com.example.mymovies.R


class MovieAdapter(private val context:Context, private val listener: IMovieAdapter): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val allMovie=ArrayList<Movie>()
    inner class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView: TextView =itemView.findViewById(R.id.movieName)
        val textView2: TextView =itemView.findViewById(R.id.directorName)
        val post:ImageView=itemView.findViewById(R.id.poster)
        val deleteButton: ImageView =itemView.findViewById(R.id.delete_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val viewHolder=MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false))
        viewHolder.deleteButton.setOnClickListener {
            listener.onItemClick(allMovie[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun getItemCount(): Int {
        return allMovie.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       val cur=allMovie[position]
        holder.textView.text=cur.name
        holder.textView2.text=cur.director
        holder.post.setImageBitmap(BitmapFactory.decodeByteArray(cur.image, 0, cur.image.size))

    }
    fun updateList(newList:List<Movie>){
        allMovie.clear()
        allMovie.addAll(newList)

        notifyDataSetChanged()
    }
}
interface IMovieAdapter{
    fun onItemClick(movie: Movie)
}