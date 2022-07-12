package com.example.food_at_home.Comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_at_home.Parse.Post;
import com.example.food_at_home.R;
import com.parse.ParseFile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> posts;

    public CommentsAdapter(Context mContext, List<Post> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCommentsUsername;
        private ImageView ivCommentsPhoto;
        private EditText etCommentsDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentsUsername = itemView.findViewById(R.id.tvCommentsUsername);
            ivCommentsPhoto = itemView.findViewById(R.id.ivCommentsPhoto);
            etCommentsDescription = itemView.findViewById(R.id.etCommentsDescription);
        }

        public void bind(Post post) {
            tvCommentsUsername.setText(post.getUser().getUsername());
            etCommentsDescription.setText(post.getDescription());
            ParseFile image = post.getImage();
            if (image != null){
                Glide.with(mContext).load(image.getUrl()).into(ivCommentsPhoto);
            }
        }
    }
}
