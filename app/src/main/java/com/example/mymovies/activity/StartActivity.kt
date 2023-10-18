package com.example.mymovies.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.mymovies.R

class StartActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val sharedIdValue = sharedPreferences.getInt("id",0)
        Log.d("share",sharedIdValue.toString())
        var btn:Button=findViewById(R.id.get_started)
        if(sharedIdValue!=0){
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putInt("id",1)
            editor.apply()
            editor.commit()
            var intent= Intent(this, AppIntro::class.java)
            startActivity(intent)
        }
    }
}