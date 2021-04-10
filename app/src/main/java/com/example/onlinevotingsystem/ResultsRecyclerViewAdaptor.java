package com.example.onlinevotingsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultsRecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ResultModal> results;
    private OnItemClickListener mItemClickListener;

    public ResultsRecyclerViewAdaptor(ArrayList<ResultModal> results) {

        this.results = results;
    }

    public void updateList(ArrayList<ResultModal> modelList) {

        this.results = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_result, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {

            final ResultModal result = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.item_image_candidate_symbol.setImageDrawable(result.partySymbol);
            genericViewHolder.item_text_candidate_name.setText(result.getName());
            genericViewHolder.item_text_no_of_votes.setText(String.valueOf(result.getNoOfVotes()));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    private ResultModal getItem(int position) {
        return results.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ResultModal result);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_image_candidate_symbol;
        private final TextView item_text_candidate_name;
        private final TextView item_text_no_of_votes;

        ViewHolder(final View itemView) {

            super(itemView);

            this.item_image_candidate_symbol = itemView.findViewById(R.id.item_image_candidate_symbol);
            this.item_text_candidate_name = itemView.findViewById(R.id.item_text_candidate_name);
            this.item_text_no_of_votes = itemView.findViewById(R.id.item_text_no_of_votes);

            itemView.setOnClickListener(view -> mItemClickListener.onItemClick(itemView, getAdapterPosition(), results.get(getAdapterPosition())));
        }
    }
}
