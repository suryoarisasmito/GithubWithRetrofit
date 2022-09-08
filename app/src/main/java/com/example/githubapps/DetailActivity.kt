package com.example.githubapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)!!
        getDetailUser(username)
    }

    private fun setDetailProfile(githubUserDetail : GithubUserDetail) {
        Glide.with(this@DetailActivity)
            .load(githubUserDetail.avatarUrl)
            .into(binding.imgDetailProfile)
        binding.tvFullnameUser.text = githubUserDetail.name
        binding.tvLocationUser.text = githubUserDetail.location
        binding.tvTwitterUser.text = githubUserDetail.twitter
    }


    private fun getDetailUser(username: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<GithubUserDetail> {
            override fun onResponse(
                call: Call<GithubUserDetail>,
                response: Response<GithubUserDetail>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val githubResponseDetailItem = response.body()
                    if (githubResponseDetailItem != null) {
                        setDetailProfile(githubResponseDetailItem)
                    }
                }
            }

            override fun onFailure(call: Call<GithubUserDetail>, t: Throwable) {
                showLoading(false)
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