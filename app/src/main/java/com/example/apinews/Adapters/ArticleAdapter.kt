package com.example.apinews.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apinews.Models.Article
import com.example.apinews.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layour_news_items.view.*

class ArticleAdapter(
        val  list:ArrayList<Article>,
        val  buttonClic: OnButtonClickListener

): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleViewHolder = //el inflado
            ArticleViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.layour_news_items ,p0,false) )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) { //mezcla de recursos, vista grafica y la lista
        return holder.bind(list[position],buttonClic)
    }


    override fun getItemCount(): Int=list.size  //Devuelve el numero de  items de nuestra lista


    //Crearemos la clase para el Binding

    class ArticleViewHolder (articleItemView: View):RecyclerView.ViewHolder(articleItemView){
        fun bind(article: Article, onButtonClickListener: OnButtonClickListener) =with(itemView){
            TextViewTitle.text=article.title
            TextViewDescription.text=article.description
            Picasso.get().load(article.urlToImage).into(ImageViewArticle)
            Btn_Share.setOnClickListener{onButtonClickListener.OnButtonShareClic(article,position)}
            Btn_news.setOnClickListener{onButtonClickListener.OnButtonNewsClic(article,position)}
        }
    }

    interface OnButtonClickListener{
        fun OnButtonNewsClic(article: Article, position:Int)
        fun OnButtonShareClic(article: Article, position:Int)
    }

}