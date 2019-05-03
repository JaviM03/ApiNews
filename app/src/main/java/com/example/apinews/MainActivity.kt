package com.example.apinews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.toolbox.Volley
import com.example.apinews.Adapters.ArticleAdapter
import com.example.apinews.Models.Article
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layour_news_items.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(),ArticleAdapter.OnButtonClickListener {
    override fun OnButtonNewsClic(article: Article, position: Int) {
        val intent =Intent(this@MainActivity,WebViewActivity::class.java)
        intent.putExtra("article",position)
        startActivity(intent)

    }

    override fun OnButtonShareClic(article: Article, position: Int) {
       val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,article.url)
        startActivity(intent)
    }

    val TAG="MainActivity.kt"
    var articleAdapter: ArticleAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        peticionNoticias()
        articleAdapter = ArticleAdapter(listaArticulos,this)
        //my_recycler.setHasFixedSize(true)//El tamaño de los items seran iguales
        my_recycler.layoutManager=LinearLayoutManager(baseContext)
        my_recycler.adapter=articleAdapter


    }

    fun peticionNoticias(){
        val colaDePeticiones = Volley.newRequestQueue(this)
        val peticionJSON = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> {
                    response ->
                    try{
                        listaArticulos.removeAll(listaArticulos)
                        listaArticulos.addAll(procesarJSON(response))
                        articleAdapter!!.notifyDataSetChanged()
                        Log.d(TAG, response.toString())

                    } catch (e: JSONException){
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener{error-> Log.d(TAG, error.toString())}
                )
            colaDePeticiones.add(peticionJSON)
    }
        fun procesarJSON(json:JSONObject):ArrayList<Article>{
            val articles: JSONArray = json.getJSONArray("articles")
            val listArticles =ArrayList<Article>()
            for (i in articles ){
                val author = i.getString("author")
                val title = i.getString("title")
                val description = i.getString("description")
                val url = i.getString("url")
                val urlImg = i.getString("urlToImage")
                listArticles.add(Article(author,title,description,url,urlImg))
            }
                return listArticles
        }
}
