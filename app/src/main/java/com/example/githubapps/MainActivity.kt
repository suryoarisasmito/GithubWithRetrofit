package com.example.githubapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapps.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager


        getUsers()
    }

    private fun setGithubProfile(profile: List<GithubUserItem>?){

        if (profile != null){
            val adapter = GithubAdapter(profile)
            binding.rvGithub.adapter = adapter
        }
    }

    private fun getUsers(){
        val client = ApiConfig.getApiService().getUser()
        client.enqueue(object : Callback<List<GithubUserItem>> {
            override fun onResponse(
                call: Call<List<GithubUserItem>>,
                response: Response<List<GithubUserItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setGithubProfile(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubUserItem>>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }

        })
    }



    private fun showLoading(isLoading : Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}