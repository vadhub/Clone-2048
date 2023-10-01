package com.abg.clone_2048;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuFragment extends Fragment {

    private Navigator navigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView fourOnFour = view.findViewById(R.id.imageViewFour);
        //ImageView fiveOnFive = view.findViewById(R.id.imageViewFive);
        ImageView sixOnSix = view.findViewById(R.id.imageViewSix);
        ImageView eightOnEight = view.findViewById(R.id.imageViewEight);

        ImageButton play = view.findViewById(R.id.playButton);

        View.OnClickListener clickListener = v -> {

            int rows = 4;

            if (v.getId() == R.id.imageViewFour) {
                rows = 4;
            }

            if (v.getId() == R.id.imageViewSix) {
                rows = 6;
            }

            if (v.getId() == R.id.imageViewEight) {
                rows = 8;
            }

            if (v.getId() == R.id.playButton) {
                navigator.startFragment(new GameFragment(), rows);
            }
        };

        fourOnFour.setOnClickListener(clickListener);
        sixOnSix.setOnClickListener(clickListener);
        eightOnEight.setOnClickListener(clickListener);


        play.setOnClickListener(clickListener);
    }

}