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

import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.R;

import java.util.LinkedList;

public class AchievementPage extends Fragment {

    private final LinkedList<Achievement> mAchievementList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private AchievementAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_achievement_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAchievementList.add(new Achievement("Piglet home builder",0,false,0));
        mAchievementList.add(new Achievement("Red riding hood",0,false,0));
        mAchievementList.add(new Achievement("follow the breadcrumbs",0,false,0));
        mAchievementList.add(new Achievement("sneaky dragon treasure thief",0,false,0));
        mAchievementList.add(new Achievement("junior story seeker",0,false,0));
        mAchievementList.add(new Achievement("master story seeker",0,false,0));


        // Create recycler view.
        mRecyclerView = getView().findViewById(R.id.AchievementRecycler);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new AchievementAdapter(getContext(), mAchievementList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
