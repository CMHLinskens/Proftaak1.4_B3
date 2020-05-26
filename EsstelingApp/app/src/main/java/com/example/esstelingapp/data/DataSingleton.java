package com.example.esstelingapp.data;

import androidx.fragment.app.FragmentManager;

public final class DataSingleton {

    private static DataSingleton INSTANCE;
    private String info = "Initial info class";
    private FragmentManager storyFragmentManager;

    private DataSingleton() {
        this.storyFragmentManager = storyFragmentManager;
    }

    public static DataSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataSingleton();
        }

        return INSTANCE;
    }

    // getters and setters
    public FragmentManager getStoryFragmentManager() { return this.storyFragmentManager; }
    public void setStoryFragmentManager(FragmentManager storyFragmentManager) {
        this.storyFragmentManager = storyFragmentManager;
    }
}