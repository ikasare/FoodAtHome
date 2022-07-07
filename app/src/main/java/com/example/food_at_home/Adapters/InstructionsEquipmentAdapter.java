package com.example.food_at_home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.Models.Equipment;
import com.example.food_at_home.Models.Ingredient;
import com.example.food_at_home.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentAdapter extends  RecyclerView.Adapter<InstructionsEquipmentAdapter.ViewHolder> {

    Context mContext;
    List<Equipment> list;

    public InstructionsEquipmentAdapter(Context mContext, List<Equipment> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.instructions_step_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stepItem = list.get(position).name;
        holder.tvInstructionStepItem.setText(stepItem);
        holder.tvInstructionStepItem.setSelected(true);
        String image = list.get(position).image;
        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/" + image).into(holder.ivInstructionStepItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivInstructionStepItem;
        TextView tvInstructionStepItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivInstructionStepItem = itemView.findViewById(R.id.ivInstructionStepItem);
            tvInstructionStepItem = itemView.findViewById(R.id.tvInstructionStepItem);
        }
    }
}
