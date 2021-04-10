package com.example.onlinevotingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PartyArrayAdapter extends ArrayAdapter {

    public PartyArrayAdapter(Context context, ArrayList<PartyModal> partyList) {

        super(context, 0, partyList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.party_spinner_row, parent, false);
        }

        ImageView imageViewLdf = convertView.findViewById(R.id.image_view_ldf);
        TextView textViewLdf = convertView.findViewById(R.id.text_view_ldf);

        PartyModal currentItem = (PartyModal) getItem(position);

        if (currentItem != null) {
            imageViewLdf.setImageResource(currentItem.getPartyImage());
            textViewLdf.setText(currentItem.getPartyName());
        }
        return convertView;
    }
}
