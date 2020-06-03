package com.example.esstelingapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esstelingapp.R;
import com.example.esstelingapp.ReadingItem;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.StoryPiecesInterface;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.json.JSonLoader;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class activity_read_story extends Fragment {
    private Story subjectStory;
    private int marker;
    private boolean TTS1playing;
    private boolean TTS3playing;
    private boolean TTS5playing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TTS1playing = false;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            subjectStory = bundle.getParcelable("storyInfo"); // Key
            try {
                marker = bundle.getInt("storyMarker");
            } catch (Exception e) {
                System.out.println("storymarker was empty");
                marker = 0;
            }
        }
        View RootView = inflater.inflate(R.layout.activity_read_story, container, false);
        ArrayList<StoryPiecesInterface> storyArrayList = subjectStory.getPieces();
        ReadingItem item = (ReadingItem) storyArrayList.get(marker);

        TextView StoryTitel = (TextView) RootView.findViewById(R.id.ReadStoryTitel);
        StoryTitel.setText(subjectStory.getStoryName());
        TextView partOfStory = (TextView) RootView.findViewById(R.id.PartialStoryProgress);
        String text = "part " + (marker + 1) + " of " + subjectStory.getPieces().size();
        partOfStory.setText(text);

        TextView storyPartOneView = (TextView) RootView.findViewById(R.id.StoryPartOneView);
        ImageView storyPartTwoView = (ImageView) RootView.findViewById(R.id.StoryPartTwoView);
        TextView storyPartThreeView = (TextView) RootView.findViewById(R.id.StoryPartThreeView);
        ImageView storyPartFourView = (ImageView) RootView.findViewById(R.id.StoryPartFourView);
        TextView storyPartFiveView = (TextView) RootView.findViewById(R.id.StoryPartFiveView);
        Button storyPartOneButton = (Button) RootView.findViewById(R.id.StoryPartOneButton);
        storyPartOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTS1playing = !TTS1playing;
                TTS3playing = false;
                TTS5playing = false;
            }
        });
        Button storyPartThreeButton = (Button) RootView.findViewById(R.id.StoryPartThreeButton);
        storyPartThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTS1playing = false;
                TTS3playing = !TTS3playing;
                TTS5playing = false;
            }
        });
        Button storyPartFiveButton = (Button) RootView.findViewById(R.id.StoryPartFiveButton);
        storyPartFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTS1playing = false;
                TTS3playing = false;
                TTS5playing = !TTS5playing;
            }
        });

        if (!item.getStoryPartOne().isEmpty()) {
            storyPartOneView.setText(item.getStoryPartOne());
            //connect audio to button for tts
        } else {
            storyPartOneView.setVisibility(View.INVISIBLE);
            storyPartOneView.setHeight(0);
            storyPartOneButton.setHeight(0);
            storyPartOneButton.setVisibility(View.INVISIBLE);
        }
        if (item.getStoryPartTwo() != -1) {
            //set image
        } else {
            storyPartTwoView.setVisibility(View.INVISIBLE);
            storyPartTwoView.requestLayout();
            storyPartTwoView.getLayoutParams().height = 60;
        }
        if (!item.getStoryPartThree().isEmpty()) {
            storyPartThreeView.setText(item.getStoryPartThree());
            //connect audio to button for tts
        } else {
            storyPartThreeView.setVisibility(View.INVISIBLE);
            storyPartThreeView.setHeight(0);
            storyPartThreeButton.setHeight(0);
            storyPartThreeButton.setVisibility(View.INVISIBLE);
        }
        if (item.getStoryPartFour() != -1) {
            //set image
        } else {
            storyPartFourView.setVisibility(View.INVISIBLE);
        }
        if (!item.getStoryPartFive().isEmpty()) {
            storyPartFiveView.setText(item.getStoryPartFive());
            //connect audio to button for tts
        } else {
            storyPartFiveView.setVisibility(View.INVISIBLE);
            storyPartFiveView.setHeight(0);
            storyPartFiveButton.setHeight(0);
            storyPartFiveButton.setVisibility(View.INVISIBLE);
        }
        Button nextStoryPiece = RootView.findViewById(R.id.nextStoryPieceButton);
        nextStoryPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker++;
                if (marker<subjectStory.getPieces().size()){
                Fragment readstoryFragment = new activity_read_story();
                Bundle bundle = new Bundle();

                bundle.putInt("storyMarker", marker);
                bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                readstoryFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();}
                else {
                    Fragment storylistFragment = new StoryPage();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,storylistFragment).commit();
                }
            }
        });

        return RootView;
    }
}
