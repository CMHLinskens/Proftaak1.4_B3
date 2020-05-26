package com.example.esstelingapp.ui;

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

import java.util.LinkedList;


public class StoryPage extends Fragment {
    private final LinkedList<Story> mStoryList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_story_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStoryList.add(new Story("3 Biggetjes", R.drawable.threepigs,false, 0));
        mStoryList.add(new Story("Hans en grietje", R.drawable.hansgretel,true,30));
        mStoryList.add(new Story("Roodkapje",R.drawable.redriding,true,70));
        mStoryList.add(new Story("Draak blaaskaak", R.drawable.blaaskaak,true,0));
        mStoryList.add(new Story("Tutorial",R.drawable.tutorial,true,100));

        // Create recycler view.
        mRecyclerView = getView().findViewById(R.id.StoryRecycler);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new StoryAdapter(getContext(), mStoryList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
