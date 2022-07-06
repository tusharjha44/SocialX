package com.example.socialx

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialx.databinding.ListItemBinding
import com.example.socialx.model.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter(): RecyclerView.Adapter<MyViewHolder>() {

    private val callBack = object: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

class MyViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article){

        binding.tvDescription.text = article.description
        binding.tvPublishedAt.text = Utils.covertTimeToText(article.publishedAt)
        binding.tvTitle.text = article.title
        binding.tvSource.text = article.source?.name

        Glide.with(binding.ivArticleImage.context)
            .load(article.urlToImage)
            .centerCrop()
            .into(binding.ivArticleImage)


    }

    fun time(){

    }


}
