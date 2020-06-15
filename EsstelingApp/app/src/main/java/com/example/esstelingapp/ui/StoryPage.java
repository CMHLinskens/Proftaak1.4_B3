package com.example.esstelingapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.data.ThemeState;

import java.util.LinkedList;


public class StoryPage extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_COLOUR_BLIND_THEME = "colour_blind_theme";

    private final LinkedList<Story> mStoryList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataSingleton.getInstance().setStoryFragmentManager(getFragmentManager());
        return inflater.inflate(R.layout.activity_story_menu, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStoryList.addAll(DataSingleton.getInstance().getStories());
//        mStoryList.add(new Story("3 Biggetjes", R.drawable.threepigs,false, new ArrayList<StoryPiecesInterface>(), 0,0,0,0));
//        mStoryList.add(new Story("Hans en grietje", R.drawable.hansgretel,true,new ArrayList<StoryPiecesInterface>(),30,0,0,0));
//        mStoryList.add(new Story("Roodkapje",R.drawable.redriding,true,new ArrayList<StoryPiecesInterface>(), 70, 0,0,0));
//        mStoryList.add(new Story("Draak blaaskaak", R.drawable.blaaskaak,true,new ArrayList<StoryPiecesInterface>(),0, 0,0 ,0 ));
//        mStoryList.add(new Story("Tutorial",R.drawable.tutorial,true,new ArrayList<StoryPiecesInterface>(), 100,0,0,0));

        SharedPreferences sharedPreferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Boolean isColorBlind = sharedPreferences.getBoolean(PREF_COLOUR_BLIND_THEME, false);
        if (isColorBlind){
            getView().setBackgroundResource(R.drawable.old_paper_cb);
        }else {
            getView().setBackgroundResource(R.drawable.old_paper);
        }


        // Create recycler view.
        mRecyclerView = getView().findViewById(R.id.StoryRecycler);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new StoryAdapter(getContext(), mStoryList, this);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
