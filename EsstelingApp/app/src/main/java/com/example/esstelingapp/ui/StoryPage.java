package com.example.esstelingapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.DragAndDropPermissions;
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

import java.util.LinkedList;


public class StoryPage extends Fragment {
    private final LinkedList<Story> mStoryList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;
    private static final String USER_DATA = "userData";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String USER_POINTS = "points";
    private static final String PROGRESS = "progress";
    private static final String STORY_COMPLETE = "storyComplete";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataSingleton.getInstance().setStoryFragmentManager(getFragmentManager());
        return inflater.inflate(R.layout.activity_story_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStoryList.addAll(DataSingleton.getInstance().getStories());

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
