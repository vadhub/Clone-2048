package com.abg.clone_2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GameFragment extends Fragment {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCORE = "score";
    private static final String HIGH_SCORE = "high score temp";
    private static final String UNDO_SCORE = "undo score";
    private static final String CAN_UNDO = "can undo";
    private static final String UNDO_GRID = "undo";
    private static final String GAME_STATE = "game state";
    private static final String UNDO_GAME_STATE = "undo game state";
    private MainView mView;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout frameLayout = view.findViewById(R.id.game_frame_layout);
        mView = ((MainActivity) requireActivity()).getMainView();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        mView.hasSaveState = settings.getBoolean("save_state", false);

        if (savedInstanceState != null)
            if (savedInstanceState.getBoolean("hasState"))
                load();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(params);

        frameLayout.addView(mView);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("hasState", true);
        save();
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void save() {
        final int rows = 4;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        Tile[][] field = mView.game.grid.field;
        Tile[][] undoField = mView.game.grid.undoField;
        editor.putInt(WIDTH + rows, field.length);
        editor.putInt(HEIGHT + rows, field.length);

        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null)
                    editor.putInt(rows + " " + xx + " " + yy, field[xx][yy].getValue());
                else
                    editor.putInt(rows + " " + xx + " " + yy, 0);

                if (undoField[xx][yy] != null)
                    editor.putInt(UNDO_GRID + rows + " " + xx + " " + yy, undoField[xx][yy].getValue());
                else
                    editor.putInt(UNDO_GRID + rows + " " + xx + " " + yy, 0);
            }
        }

        // game values:
        editor.putLong(SCORE + rows, mView.game.score);
        editor.putLong(HIGH_SCORE + rows, mView.game.highScore);
        editor.putLong(UNDO_SCORE + rows, mView.game.lastScore);
        editor.putBoolean(CAN_UNDO + rows, mView.game.canUndo);
        editor.putInt(GAME_STATE + rows, mView.game.gameState);
        editor.putInt(UNDO_GAME_STATE + rows, mView.game.lastGameState);
        editor.apply();
    }

    private void load() {
        final int rows = 5;

        //Stopping all animations
        mView.game.aGrid.cancelAnimations();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        for (int xx = 0; xx < mView.game.grid.field.length; xx++) {
            for (int yy = 0; yy < mView.game.grid.field[0].length; yy++) {
                int value = settings.getInt(rows + " " + xx + " " + yy, -1);
                if (value > 0)
                    mView.game.grid.field[xx][yy] = new Tile(xx, yy, value);
                else if (value == 0)
                    mView.game.grid.field[xx][yy] = null;

                int undoValue = settings.getInt(UNDO_GRID + rows + " " + xx + " " + yy, -1);
                if (undoValue > 0)
                    mView.game.grid.undoField[xx][yy] = new Tile(xx, yy, undoValue);
                else if (value == 0)
                    mView.game.grid.undoField[xx][yy] = null;
            }
        }

        mView.game.score = settings.getLong(SCORE + rows, mView.game.score);
        mView.game.highScore = settings.getLong(HIGH_SCORE + rows, mView.game.highScore);
        mView.game.lastScore = settings.getLong(UNDO_SCORE + rows, mView.game.lastScore);
        mView.game.canUndo = settings.getBoolean(CAN_UNDO + rows, mView.game.canUndo);
        mView.game.gameState = settings.getInt(GAME_STATE + rows, mView.game.gameState);
        mView.game.lastGameState = settings.getInt(UNDO_GAME_STATE + rows, mView.game.lastGameState);
    }

}