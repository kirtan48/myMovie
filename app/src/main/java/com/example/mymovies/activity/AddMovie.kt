package com.example.mymovies.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.room.Movie
import com.example.mymovies.viewModel.MovieViewModel
import com.example.mymovies.R

class AddMovie : AppCompatActivity() {
    lateinit var viewModel: MovieViewModel
    private val IMAGE_PICK_CODE = 100
    private var  imageUri: Uri?=null
    lateinit var tv_uploaded:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        val addbtn: Button = findViewById(R.id.btn_add)
      tv_uploaded=findViewById(R.id.img_uploaded)
        tv_uploaded.visibility=View.INVISIBLE


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MovieViewModel::class.java)

        val upload: Button = findViewById(R.id.upload)
        upload.setOnClickListener {
            selectImageFromGallery()

        }
        addbtn.setOnClickListener {
            val mvname: EditText = findViewById(R.id.tv_mvName)
            val dtrname: EditText = findViewById(R.id.tv_dtrName)

            if (mvname.text.isNotEmpty() && dtrname.text.isNotEmpty() ) {

                if (imageUri != null) {
                    val inputStream = contentResolver.openInputStream(imageUri!!)
                    val imageByteArray = inputStream?.readBytes()
                    imageByteArray?.let { bytes ->
                        viewModel.insertMovie(
                            Movie(
                                mvname.text.toString(),
                                dtrname.text.toString(),
                                bytes
                            )
                        )
                        val movie = Movie("Movie Title", "Movie Director", imageByteArray)
                    }
                    /* viewModel.insertMovie(Movie(mvname.text.toString(),dtrname.text.toString()))*/
                    Toast.makeText(this, "${mvname.text.toString()} Inserted", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Upload Poster of movie",Toast.LENGTH_SHORT).show()

                    tv_uploaded.visibility=View.VISIBLE
                    tv_uploaded.text="*upload movie poster"
                    tv_uploaded.setTextColor(Color.parseColor("#C70039"))
                }
            }
            else{
                Toast.makeText(this,"All fields are Required",Toast.LENGTH_SHORT).show()
                if(imageUri == null){
                    tv_uploaded.visibility=View.VISIBLE
                    tv_uploaded.text="*upload movie poster"
                    tv_uploaded.setTextColor(Color.parseColor("#C70039"))
                }

            }
        }

    }
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data // Get the URI of the selected image
            tv_uploaded.visibility=View.VISIBLE
            tv_uploaded.text="*poster uploaded"
            tv_uploaded.setTextColor(Color.parseColor("#79AC78"))



        }
    }

}