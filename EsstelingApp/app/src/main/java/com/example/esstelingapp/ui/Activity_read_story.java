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
import android.graphics.drawable.Drawable;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esstelingapp.ActionItem;
import com.example.esstelingapp.Action_window;
import com.example.esstelingapp.GameItem;
import com.example.esstelingapp.R;
import com.example.esstelingapp.ReadingItem;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.StoryPiecesInterface;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.games.RiddlePage;

import java.util.ArrayList;

public class Activity_read_story extends Fragment {
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

        final Button storyPartOneButton = (Button) RootView.findViewById(R.id.StoryPartOneButton);
        storyPartOneButton.setBackgroundResource(R.drawable.sound_off);

        final Button storyPartThreeButton = (Button) RootView.findViewById(R.id.StoryPartThreeButton);
        storyPartThreeButton.setBackgroundResource(R.drawable.sound_off);

        final Button storyPartFiveButton = (Button) RootView.findViewById(R.id.StoryPartFiveButton);
        storyPartFiveButton.setBackgroundResource(R.drawable.sound_off);

        storyPartOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyPartOneButton.setSelected(!storyPartOneButton.isSelected());

                if (storyPartOneButton.isSelected()){
                    storyPartOneButton.setBackgroundResource(R.drawable.sound_on);
                    storyPartThreeButton.setBackgroundResource(R.drawable.sound_off);
                    storyPartFiveButton.setBackgroundResource(R.drawable.sound_off);
                }else {
                    storyPartOneButton.setBackgroundResource(R.drawable.sound_off);
                }

                TTS1playing = !TTS1playing;
                TTS3playing = false;
                TTS5playing = false;
            }
        });

        storyPartThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyPartThreeButton.setSelected(!storyPartThreeButton.isSelected());
                if (storyPartThreeButton.isSelected()){
                    storyPartOneButton.setBackgroundResource(R.drawable.sound_off);
                    storyPartThreeButton.setBackgroundResource(R.drawable.sound_on);
                    storyPartFiveButton.setBackgroundResource(R.drawable.sound_off);
                }else {
                    storyPartThreeButton.setBackgroundResource(R.drawable.sound_off);
                }

                TTS1playing = false;
                TTS3playing = !TTS3playing;
                TTS5playing = false;
            }
        });

        storyPartFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyPartFiveButton.setSelected(!storyPartFiveButton.isSelected());
                if (storyPartFiveButton.isSelected()){
                    storyPartOneButton.setBackgroundResource(R.drawable.sound_off);
                    storyPartThreeButton.setBackgroundResource(R.drawable.sound_off);
                    storyPartFiveButton.setBackgroundResource(R.drawable.sound_on);
                }else {
                    storyPartFiveButton.setBackgroundResource(R.drawable.sound_off);
                }

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
        if (!item.getStoryPartTwo().isEmpty()) {
            storyPartTwoView.getLayoutParams().height = 850;
            storyPartTwoView.getLayoutParams().width = 850;
            int id = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(item.getStoryPartTwo(), "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
            storyPartTwoView.setImageResource(id);
        } else {
            storyPartTwoView.setVisibility(View.INVISIBLE);
            storyPartTwoView.getLayoutParams().height = 50;
            storyPartTwoView.getLayoutParams().width = 50;
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
        if (!item.getStoryPartFour().isEmpty()) {
            storyPartFourView.getLayoutParams().height = 850;
            storyPartFourView.getLayoutParams().width = 850;
            int id = DataSingleton.getInstance().getMainContext().getResources().getIdentifier(item.getStoryPartFour(), "drawable", DataSingleton.getInstance().getMainContext().getPackageName());
            storyPartFourView.setImageResource(id);
        } else {
            storyPartFourView.setVisibility(View.INVISIBLE);
            storyPartFourView.getLayoutParams().height = 50;
            storyPartFourView.getLayoutParams().width = 50;
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
                if (marker<subjectStory.getPieces().size()) {
                    if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                        Fragment readstoryFragment = new Activity_read_story();
                        Bundle bundle = new Bundle();

                        bundle.putInt("storyMarker", marker);
                        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                        readstoryFragment.setArguments(bundle);

                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();
                    }
                    else if (subjectStory.getPieces().get(marker) instanceof GameItem){
                        Fragment riddlePage = new RiddlePage();
                        Bundle bundle = new Bundle();

                        bundle.putInt("storyMarker", marker);
                        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                        riddlePage.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, riddlePage).commit();
                    }
                    else if(subjectStory.getPieces().get(marker)instanceof ActionItem){
                        Fragment actionWindow = new Action_window();
                        Bundle bundle = new Bundle();

                        bundle.putInt("storyMarker", marker);
                        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                        actionWindow.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, actionWindow).commit();
                    }
                }
                else {
                    Fragment storylistFragment = new StoryPage();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,storylistFragment).commit();
                }
            }
        });

        ScrollView scrollview = RootView.findViewById(R.id.storyScrollView);


        scrollview.setOnTouchListener(new OnSwipeTouchListener(container.getContext()){

            @Override
            public void onSwipeRight() {
                if (marker == 0){

                }else {
                    marker--;
                    if (marker < subjectStory.getPieces().size()) {
                        if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                            Fragment readstoryFragment = new Activity_read_story();
                            Bundle bundle = new Bundle();

                            bundle.putInt("storyMarker", marker);
                            bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                            readstoryFragment.setArguments(bundle);

                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();
                        } else if (subjectStory.getPieces().get(marker) instanceof GameItem) {
                            Fragment riddlePage = new RiddlePage();
                            Bundle bundle = new Bundle();

                            bundle.putInt("storyMarker", marker);
                            bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                            riddlePage.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, riddlePage).commit();
                        }
                    } else {
                        Fragment storylistFragment = new StoryPage();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, storylistFragment).commit();
                    }
                }
            }

            @Override
            public void onSwipeLeft() {
                marker++;
                if (marker < subjectStory.getPieces().size()) {
                    if (subjectStory.getPieces().get(marker) instanceof ReadingItem) {
                        Fragment readstoryFragment = new Activity_read_story();
                        Bundle bundle = new Bundle();

                        bundle.putInt("storyMarker", marker);
                        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                        readstoryFragment.setArguments(bundle);

                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();
                    } else if (subjectStory.getPieces().get(marker) instanceof GameItem) {
                        Fragment riddlePage = new RiddlePage();
                        Bundle bundle = new Bundle();

                        bundle.putInt("storyMarker", marker);
                        bundle.putParcelable("storyInfo", subjectStory);  // Key, value
                        riddlePage.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, riddlePage).commit();
                    }
                } else {
                    Fragment storylistFragment = new StoryPage();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, storylistFragment).commit();
                }
            }
        });

        RootView.setOnTouchListener(new OnSwipeTouchListener(container.getContext()){

            @Override
            public void onSwipeRight() {
                marker--;
                if (marker<subjectStory.getPieces().size()){
                    Fragment readstoryFragment = new Activity_read_story();
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

            @Override
            public void onSwipeLeft() {
                marker++;
                if (marker<subjectStory.getPieces().size()){
                    Fragment readstoryFragment = new Activity_read_story();
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
