package com.example.food_at_home.Bookmarks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.Comments.CommentsAdapter;
import com.example.food_at_home.Parse.Bookmarks;
import com.example.food_at_home.R;
import com.example.food_at_home.RecipeDetails.RecipeDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {

    private Context mContext;
    private List<Bookmarks> bookmarksList;

    public BookmarksAdapter(Context mContext, List<Bookmarks> bookmarksList) {
        this.mContext = mContext;
        this.bookmarksList = bookmarksList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = bookmarksList.get(position).getBookmarkTitle();
        holder.tvTitle.setText(title);
        holder.tvTitle.setSelected(true);
        holder.tvLikes.setText("20 likes");
        holder.tvServings.setText("2 servings");
        holder.tvTime.setText("45 minutes");
        String imgUri = bookmarksList.get(position).getBookmarkImage();
        Picasso.get().load(imgUri).into(holder.ivFoodPhoto);
    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cvRandomRecipe;
        TextView tvTitle;
        ImageView ivFoodPhoto;
        TextView tvServings;
        TextView tvLikes;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // assign ids to widgets
            cvRandomRecipe = itemView.findViewById(R.id.cvRandomRecipe);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFoodPhoto = itemView.findViewById(R.id.ivFoodPhoto);
            tvServings = itemView.findViewById(R.id.tvServings);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
