package com.example.esstelingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.R;
import com.jackandphantom.blurimage.BlurImage;

public class HomePage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finding the background image and making it blurry
        ImageView backgroundImage = (ImageView) getView().findViewById(R.id.background_image);
        BlurImage.with(getContext()).load(R.drawable.home_background_image).intensity(20).Async(true).into(backgroundImage);
    }
}
