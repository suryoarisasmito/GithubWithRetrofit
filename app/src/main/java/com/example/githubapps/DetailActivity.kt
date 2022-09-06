package com.example.githubapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.githubapps.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    companion object{
        const val EXTRA_USERNAME = "username"
        const val EXTRA_FULLNAME = "fullname"
        const val EXTRA_LOCATION = "location"
        const val EXTRA_TWITTER = "twitter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDetailUser()

        Log.d("DetailActivity", "${getDetailUser()}")
    }

    private fun setDetailProfile(githubResponseDetailItem : GithubResponseDetailItem) {

        Glide.with(this@DetailActivity)
            .load("https://api.github.com/users/${githubResponseDetailItem.login}")
            .into(binding.imgDetailProfile)
        binding.tvFullnameUser.text = githubResponseDetailItem.name
        binding.tvLocationUser.text = githubResponseDetailItem.location
        binding.tvTwitterUser.text = githubResponseDetailItem.twitter



    }


    private fun getDetailUser(){
        val client = ApiConfig.getApiService().getDetailUser(EXTRA_USERNAME)
        client.enqueue(object : Callback<List<GithubResponseDetailItem>> {
            override fun onResponse(
                call: Call<List<GithubResponseDetailItem>>,
                response: Response<List<GithubResponseDetailItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setDetailProfile(GithubResponseDetailItem(EXTRA_USERNAME, EXTRA_FULLNAME,
                            EXTRA_LOCATION, EXTRA_TWITTER))
                    }
                }
            }

            override fun onFailure(call: Call<List<GithubResponseDetailItem>>, t: Throwable) {
                Log.e("DetailActivity", "${t.message}")
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