package com.example.mymovies.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.adapter.ViewPageAdapter
import me.relex.circleindicator.CircleIndicator3

class AppIntro : AppCompatActivity() , ViewPageAdapter.FinishButtonClickListener {
    private var imageList= mutableListOf<Int>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_intro)
        val skip:Button=findViewById(R.id.skipButton)
        skip.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        addToList()
        var pager:ViewPager2=findViewById(R.id.slideViewPager)
        val adapter= ViewPageAdapter(imageList,this)
        pager.adapter=adapter
        pager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        val finishButton:Button?  = findViewById(R.id.finish)
        finishButton?.setOnClickListener {
            // Handle the "Finish" button click event here
            Log.d("finish","finish")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    val indicator:CircleIndicator3=findViewById(R.id.indicator)
        indicator.setViewPager(pager)
    }
    private fun addToList(){
        imageList.add(R.drawable.screen1)
        imageList.add(R.drawable.screen2)
        imageList.add(R.drawable.screen3)
        imageList.add(R.drawable.screen4)
    }
    override fun onFinishButtonClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}