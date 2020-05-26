package com.example.esstelingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.LinkedList;

public class StoryMenuActivity extends AppCompatActivity {

    private final LinkedList<Story> mStoryList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_menu);
        mStoryList.add(new Story("3 Biggetjes", R.drawable.threepigs,false, 0));
        mStoryList.add(new Story("Hans en grietje", R.drawable.hansgretel,true,30));
        mStoryList.add(new Story("Roodkapje",R.drawable.redriding,true,70));
        mStoryList.add(new Story("Draak blaaskaak", R.drawable.blaaskaak,true,0));
        mStoryList.add(new Story("Tutorial",R.drawable.tutorial,true,100));

// Create recycler view.
        mRecyclerView = findViewById(R.id.StoryRecycler);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new StoryAdapter(this, mStoryList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
