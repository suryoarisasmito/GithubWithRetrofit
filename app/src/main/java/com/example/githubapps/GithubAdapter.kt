package com.example.githubapps

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GithubAdapter(private val listGithub : List<GithubResponseItem>) : RecyclerView.Adapter<GithubAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_profile, viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem.text = listGithub[position].login
        Glide.with(viewHolder.itemView)
            .load(listGithub[position].avatarUrl)
            .into(viewHolder.imgUsername)

        val context = viewHolder.itemView.context

        viewHolder.btnDetail.setOnClickListener {
            val moveMoreDetail = Intent(context, DetailActivity::class.java)
            moveMoreDetail.putExtra(DetailActivity.EXTRA_USERNAME, listGithub[position].login)
            context.startActivity(moveMoreDetail)
        }
    }

    override fun getItemCount(): Int = listGithub.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem : TextView = view.findViewById(R.id.tv_username)
        val imgUsername : ImageView = view.findViewById(R.id.img_profile_github)
        val btnDetail : Button = view.findViewById(R.id.btn_detail)
    }

}