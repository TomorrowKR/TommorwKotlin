package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        // RecyclerView 설정
        val recyclerViewPosts = findViewById<RecyclerView>(R.id.recycler_view_posts)
        recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        recyclerViewPosts.adapter = PostsAdapter(getDummyPosts())
    }

    private fun getDummyPosts(): List<Post> {
        // 더미	데이터	생성
        return listOf(
            Post("첫 번째 포스트", "포스트 내용 1", R.drawable.ic_image_placeholder, "10분 전"),
            Post("두 번째 포스트", "포스트 내용 2", R.drawable.ic_image_placeholder, "1시간 전"),
            Post("세 번째 포스트", "포스트 내용 3", R.drawable.ic_image_placeholder, "어제")
        )
    }
}
