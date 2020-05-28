package com.example.esstelingapp.data;

import androidx.fragment.app.FragmentManager;

public final class DataSingleton {

    private static DataSingleton INSTANCE;
    private String info = "Initial info class";

    private DataSingleton() {

    }

    public static DataSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataSingleton();
        }

        return INSTANCE;
    }

    // getters and setters

}