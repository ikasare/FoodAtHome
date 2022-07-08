package com.example.food_at_home.RecipeDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_at_home.R;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepAdapter.ViewHolder> {

    Context mContext;
    List<Step> list;

    public InstructionStepAdapter(Context mContext, List<Step> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.instructions_steps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stepNumber = String.valueOf(list.get(position).number);
        holder.tvInstructionsStepNumber.setText(stepNumber);
        String step = list.get(position).step;
        holder.tvInstructionsStepTitle.setText(step);

        holder.rvInstructionIngredients.setHasFixedSize(true);
        holder.rvInstructionIngredients.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter(mContext, list.get(position).ingredients);
        holder.rvInstructionIngredients.setAdapter(instructionsIngredientsAdapter);

        holder.rvEquipment.setHasFixedSize(true);
        holder.rvEquipment.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter = new InstructionsEquipmentAdapter(mContext, list.get(position).equipment);
        holder.rvEquipment.setAdapter(instructionsEquipmentAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInstructionsStepNumber, tvInstructionsStepTitle;
        RecyclerView rvEquipment, rvInstructionIngredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstructionsStepNumber = itemView.findViewById(R.id.tvInstructionsStepNumber);
            tvInstructionsStepTitle = itemView.findViewById(R.id.tvInstructionsStepTitle);
            rvEquipment = itemView.findViewById(R.id.rvEquipment);
            rvInstructionIngredients = itemView.findViewById(R.id.rvInstructionIngredients);
        }

    }
}
