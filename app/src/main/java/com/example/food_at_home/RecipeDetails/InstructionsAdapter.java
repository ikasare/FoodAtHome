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

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {

    Context mContext;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context mContext, List<InstructionsResponse> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.intructions_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = list.get(position).name;
        holder.tvInstructionName.setText(name);
        holder.rvSteps.setHasFixedSize(true);
        holder.rvSteps.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        InstructionStepAdapter stepAdapter = new InstructionStepAdapter(mContext, list.get(position).steps);
        holder.rvSteps.setAdapter(stepAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInstructionName;
        RecyclerView rvSteps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInstructionName = itemView.findViewById(R.id.tvInstructionName);
            rvSteps = itemView.findViewById(R.id.rvSteps);
        }
    }
}
