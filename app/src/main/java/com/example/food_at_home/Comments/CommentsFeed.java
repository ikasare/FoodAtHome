package com.example.food_at_home.Comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.food_at_home.Parse.Meal;
import com.example.food_at_home.Parse.Post;
import com.example.food_at_home.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CommentsFeed extends AppCompatActivity {
    private RecyclerView rvComments;
    protected CommentsAdapter adapter;
    protected List<Post> allPosts;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_feed);

        id = getIntent().getStringExtra("id");

        rvComments = findViewById(R.id.rvComments);
        allPosts = new ArrayList<>();
        adapter = new CommentsAdapter(this, allPosts);

        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        queryComments();
    }

    private void queryComments(){

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_MEAL);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");

        query.whereEqualTo("post_meal", id);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e("CommentsFeed", e.getMessage());
                    return;
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}