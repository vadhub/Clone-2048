package com.abg.clone_2048;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicInteger;

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
        ImageView fiveOnFive = view.findViewById(R.id.imageViewFive);
        ImageView sixOnSix = view.findViewById(R.id.imageViewSix);
        ImageView eightOnEight = view.findViewById(R.id.imageViewEight);

        View filter4 = view.findViewById(R.id.filter4x4);
        View filter5 = view.findViewById(R.id.filter5x5);
        View filter6 = view.findViewById(R.id.filter6x6);
        View filter8 = view.findViewById(R.id.filter8x8);

        ImageButton play = view.findViewById(R.id.playButton);

        AtomicInteger rows = new AtomicInteger(4);

        View.OnClickListener clickListener = v -> {

            if (v.getId() == R.id.imageViewFour) {
                rows.set(4);
                filter4.setVisibility(View.GONE);
                filter5.setVisibility(View.VISIBLE);
                filter6.setVisibility(View.VISIBLE);
                filter8.setVisibility(View.VISIBLE);
            }

            if (v.getId() == R.id.imageViewFive) {
                rows.set(5);
                filter4.setVisibility(View.VISIBLE);
                filter5.setVisibility(View.GONE);
                filter6.setVisibility(View.VISIBLE);
                filter8.setVisibility(View.VISIBLE);
            }

            if (v.getId() == R.id.imageViewSix) {
                rows.set(6);
                filter4.setVisibility(View.VISIBLE);
                filter5.setVisibility(View.VISIBLE);
                filter6.setVisibility(View.GONE);
                filter8.setVisibility(View.VISIBLE);
            }

            if (v.getId() == R.id.imageViewEight) {
                rows.set(8);
                filter4.setVisibility(View.VISIBLE);
                filter5.setVisibility(View.VISIBLE);
                filter6.setVisibility(View.VISIBLE);
                filter8.setVisibility(View.GONE);
            }

            Log.d("rows", rows.get()+"");

            if (v.getId() == R.id.playButton) {
                Log.d("rows", rows.get()+"");
                navigator.startFragment(new GameFragment(), rows.get());
            }
        };

        fourOnFour.setOnClickListener(clickListener);
        fiveOnFive.setOnClickListener(clickListener);
        sixOnSix.setOnClickListener(clickListener);
        eightOnEight.setOnClickListener(clickListener);
        play.setOnClickListener(clickListener);
    }

}