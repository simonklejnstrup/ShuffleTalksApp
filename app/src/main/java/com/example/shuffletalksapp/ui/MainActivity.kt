package com.example.shuffletalksapp.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shuffletalksapp.R
import com.example.shuffletalksapp.databinding.ActivityMainBinding
import com.example.shuffletalksapp.session.SessionManager

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sessionManager = SessionManager()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavController()


    }

    override fun onStart() {
        super.onStart()

        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            navBtnHome.setOnClickListener {
                navController.navigate(R.id.homeFragment)
            }
            navBtnFeed.setOnClickListener {
                navController.navigate(R.id.feedFragment)
            }
            navBtnProfile.setOnClickListener {
                if (sessionManager.isLoggedIn()) {
                    navController.navigate(R.id.profileFragment)
                } else {
                    navController.navigate(R.id.loginFragment)
                }


            }
        }
    }


    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

    }


    // REtrofit stuff
    /*
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavController()

        //val repository = Repository()
        //val viewModelFactory = FeedViewModelFactory(repository)
        //viewModel = ViewModelProvider(this, viewModelFactory).get(FeedViewModel::class.java)
        //val _post = Post("628f6bb51081edec4463f290", emptyArray())
        //val post = viewModel.getPost(_post)


        val post = Post(
                "628f6bb51081edec4463f290",
            arrayOf(
                Comment(
                    "628e3c0ef24148cf460a1671",
                    "SpørgeSøren",
                    "Se mit nye, fede sæt EDIT",
                    "628f6bb51081edec4463f291"
                )
            )
        )
        //addCard(post)


        //viewModel.response.observe(this, Observer { response ->
        //    println("12345" + response.isSuccessful)
        //    Log.d("12345", response.isSuccessful.toString())
        //})

        viewModel.getAllPosts()
        viewModel.response.observe(this, Observer { response ->
            println("12345" + response.isSuccessful)
            Log.d("12345", response.isSuccessful.toString())
            if (response.isSuccessful) {
                println("12345 "+ response.body()?._id.toString())
                Log.d("12345", response.body()?._id.toString())
                println("6789 "+ response.body()?.userId.toString())
                //println("Response "+ response.body()?.id.toString())
                //println("Response "+ response.body()?.title)
                //println("Response "+ response.body()?.body)

            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }

    */
}