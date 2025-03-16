package com.example.eatwell.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.eatwell.AllEntriesActivity;
import com.example.eatwell.GainWeightActivity;
import com.example.eatwell.MaintainWeightActivity;
import com.example.eatwell.NotesActivity;
import com.example.eatwell.WeightLossActivity;
import com.example.eatwell.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Get references to the cards
        MaterialCardView cardWeightLoss1 = root.findViewById(R.id.cardWeightLoss1);
        MaterialCardView cardWeightLoss2 = root.findViewById(R.id.cardWeightLoss2);
        MaterialCardView cardMaintainWeight1 = root.findViewById(R.id.cardMaintainWeight1);
        MaterialCardView cardMaintainWeight2 = root.findViewById(R.id.cardMaintainWeight2);
        MaterialCardView cardGainWeight = root.findViewById(R.id.cardGainWeight);
        MaterialCardView cardnotes = root.findViewById(R.id.cardnotes);
        FloatingActionButton fab = root.findViewById(R.id.fab_add);

        // Set click listeners for weight loss cards
        View.OnClickListener weightLossListener = v -> {
            Intent intent = new Intent(getActivity(), WeightLossActivity.class);
            startActivity(intent);
        };
        cardWeightLoss1.setOnClickListener(weightLossListener);
        cardWeightLoss2.setOnClickListener(weightLossListener);

        // Set click listeners for maintain weight cards
        View.OnClickListener maintainWeightListener = v -> {
            Intent intent = new Intent(getActivity(), MaintainWeightActivity.class);
            startActivity(intent);
        };
        cardMaintainWeight1.setOnClickListener(maintainWeightListener);
        cardMaintainWeight2.setOnClickListener(maintainWeightListener);

        // Set click listener for gain weight card
        cardGainWeight.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GainWeightActivity.class);
            startActivity(intent);
        });
        cardnotes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AllEntriesActivity.class);
            startActivity(intent);
        });
        fab.setOnClickListener(v -> {
            Toast.makeText(getContext(), "FAB Clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), NotesActivity.class);
            startActivity(intent);
        });

        return root;
    }
}