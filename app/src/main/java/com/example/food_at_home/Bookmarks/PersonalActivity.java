package com.example.food_at_home.Bookmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.food_at_home.Common.LoginActivity;
import com.example.food_at_home.Common.MainActivity;
import com.example.food_at_home.R;
import com.parse.ParseUser;

public class PersonalActivity extends AppCompatActivity {

    private Button btnSeeBookmarks;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btnSeeBookmarks = findViewById(R.id.btnSeeBookmarks);
        btnSignOut = findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(PersonalActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSeeBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, BookmarksActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
}