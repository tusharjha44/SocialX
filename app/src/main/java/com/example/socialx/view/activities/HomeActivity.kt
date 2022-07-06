package com.example.socialx.view.activities


import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialx.R
import com.example.socialx.adapter.NewsListAdapter
import com.example.socialx.api.NewsService
import com.example.socialx.api.RetrofitInstance
import com.example.socialx.databinding.ActivityHomeBinding
import com.example.socialx.viewModel.NewsViewModel
import com.example.socialx.viewModel.NewsViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var service: NewsService
    private lateinit var factory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel
    private lateinit var madapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val window: Window = this.window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.blue, null)


        service = RetrofitInstance.getRetroInstance().create(NewsService::class.java)
        factory = NewsViewModelFactory(service)
        viewModel = ViewModelProvider(this,factory)[NewsViewModel::class.java]

        binding.ivLogOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this,"Signed Out Successfully!!",Toast.LENGTH_SHORT).show()
        }

        initRecyclerView()
        viewNewsList()
        setSearchView()

    }

    private fun setSearchView(){
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchedHeadLines("in,",p0.toString())
                viewSearchedList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedHeadLines("us",p0.toString())
                    viewSearchedList()
                }
                return false
            }

        })

        binding.svNews.setOnCloseListener {
            initRecyclerView()
            viewSearchedList()
            false
        }

    }

    private fun viewSearchedList() {
        viewModel.searchedHeadLines.observe(this) {
            madapter.differ.submitList(it.body()?.articles?.toList())
        }
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadlines("in")
        viewModel.newsHeadLines.observe(this) {
            madapter.differ.submitList(it.body()?.articles?.toList())
        }
    }

    private fun initRecyclerView() {
        madapter = NewsListAdapter()
        binding.rvNews.adapter = madapter
        binding.rvNews.layoutManager = LinearLayoutManager(this)
    }

}