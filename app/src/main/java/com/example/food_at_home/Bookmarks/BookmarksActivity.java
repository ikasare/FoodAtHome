package com.example.food_at_home.Bookmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.food_at_home.Parse.Bookmarks;
import com.example.food_at_home.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView rvBookmarks;
    protected List<Bookmarks> bookmarksList;
    protected BookmarksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        rvBookmarks = findViewById(R.id.rvBookmarks);
        bookmarksList = new ArrayList<>();
        adapter = new BookmarksAdapter(this, bookmarksList);

        rvBookmarks.setAdapter(adapter);
        rvBookmarks.setLayoutManager(new LinearLayoutManager(this));
        queryBookmarks();
    }

    private void queryBookmarks() {
        ParseQuery<Bookmarks> query = ParseQuery.getQuery(Bookmarks.class);
        query.include(Bookmarks.BOOKMARK_TITLE);
        query.include(Bookmarks.BOOKMARK_PHOTO);
        query.include(Bookmarks.BOOKMARK_ID);
        query.include(Bookmarks.BOOKMARK_USER);

        query.whereEqualTo(Bookmarks.BOOKMARK_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Bookmarks>() {
            @Override
            public void done(List<Bookmarks> bookmarks, ParseException e) {
                if (e != null){
                    Log.e("BookmarksActivity", e.getMessage());
                    return;
                }
                bookmarksList.addAll(bookmarks);
                adapter.notifyDataSetChanged();
            }
        });

    }
}