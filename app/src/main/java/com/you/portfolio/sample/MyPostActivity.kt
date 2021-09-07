package com.you.portfolio.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.you.portfolio.GlobalApplication
import com.you.portfolio.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_post)

        val glide: RequestManager = Glide.with(this)
        val myPostRecyclerView: RecyclerView = findViewById(R.id.mypost_recycler_view)

        (application as GlobalApplication).service.getUserPostList()
            .enqueue(object : Callback<ArrayList<Post>> {
                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val posts = response.body()
                        val adapter = posts?.let { it ->
                            MyPostAdapter(it, glide)
                        }
                        myPostRecyclerView.adapter = adapter
                        myPostRecyclerView.layoutManager = LinearLayoutManager(this@MyPostActivity)
                    } else {
                        Toast.makeText(this@MyPostActivity, response.message(), Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                    Toast.makeText(this@MyPostActivity, "onFailure", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}

class MyPostAdapter(private val posts: ArrayList<Post>, private val glide: RequestManager) :
    RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postOwner: TextView = view.findViewById(R.id.post_owner)
        val postImage: ImageView = view.findViewById(R.id.post_image)
        val postContent: TextView = view.findViewById(R.id.post_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_post_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postOwner.text = posts[position].owner
        holder.postContent.text = posts[position].content
        glide.load(posts[position].image)
            .into(holder.postImage)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}