package com.example.onlinevotingsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CandidatesRecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CandidateModal> candidates;
    private OnItemClickListener mItemClickListener;

    public CandidatesRecyclerViewAdaptor(ArrayList<CandidateModal> candidates) {

        this.candidates = candidates;
    }

    public void updateList(ArrayList<CandidateModal> modelList) {

        this.candidates = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_candidate, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {

            final CandidateModal candidate = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.item_image_candidate_symbol.setImageDrawable(candidate.partySymbol);
            genericViewHolder.item_text_candidate_name.setText(candidate.getName());
        }
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }

    private CandidateModal getItem(int position) {
        return candidates.get(position);
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position, CandidateModal candidate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_image_candidate_symbol;
        private final TextView item_text_candidate_name;
        private final Button item_button_vote;

        ViewHolder(final View itemView) {

            super(itemView);

            this.item_image_candidate_symbol = itemView.findViewById(R.id.item_image_candidate_symbol);
            this.item_text_candidate_name = itemView.findViewById(R.id.item_text_candidate_name);
            this.item_button_vote = itemView.findViewById(R.id.item_button_vote);

            item_button_vote.setOnClickListener(view -> mItemClickListener.onItemClick(itemView, getAdapterPosition(), candidates.get(getAdapterPosition())));
        }
    }
}
