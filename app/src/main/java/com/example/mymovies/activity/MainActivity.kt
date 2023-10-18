package com.example.mymovies.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.adapter.IMovieAdapter
import com.example.mymovies.room.Movie
import com.example.mymovies.adapter.MovieAdapter
import com.example.mymovies.viewModel.MovieViewModel
import com.example.mymovies.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), IMovieAdapter {
    lateinit var viewModel: MovieViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter= MovieAdapter(this,this)
        var total=adapter.itemCount
        val cd:ImageView=findViewById(R.id.img_nodata)
        if(total==0){
            cd.visibility= View.VISIBLE
        }
        else{
            cd.visibility= View.INVISIBLE
        }
        recyclerView.adapter=adapter
        val add:FloatingActionButton=findViewById(R.id.add)

        mAuth = FirebaseAuth.getInstance()
        val auth = Firebase.auth
        val user = auth.currentUser



        add.setOnClickListener {

            if (user != null) {
                val intent= Intent(this, AddMovie::class.java)
                startActivity(intent)


            } else {

                signIn()
            }

        }



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        viewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MovieViewModel::class.java)
        viewModel.allMovie.observe(this, Observer {list->
            list?.let{
                adapter.updateList(it)
                if(it.isEmpty()){
                    cd.visibility= View.VISIBLE
                }
                else{
                    cd.visibility= View.INVISIBLE
                }
            }

        })

    }


    override fun onItemClick(movie: Movie) {
    viewModel.deleteMovie(movie)
        Toast.makeText(this,"${movie.name} deleted",Toast.LENGTH_SHORT).show()

    }
    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        Log.d("account","entered")
       launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        Log.d("account","entered")
        Log.d("account","${result.resultCode } and    ${Activity.RESULT_OK}")

        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            Log.d("account","entered")
            handleResults(task)
        }
        Log.d("account","no")
    }
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            Log.d("account","$account entered")
            if (account != null){
                val intent= Intent(this, AddMovie::class.java)
                startActivity(intent)
            }
        }else{
            Log.d("account","entered")
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }
}