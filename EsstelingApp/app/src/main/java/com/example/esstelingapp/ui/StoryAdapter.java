package com.example.esstelingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;
import com.example.esstelingapp.data.DataSingleton;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private final LinkedList<Story> mStoryList;
    private final LayoutInflater mInflater;
    private Fragment storyMenuFragment;
    private static final String PREFS_NAME = "prefs";
    private boolean isColourBlind;

    private static final String USER_DATA = "userData";
    private static final String USER_POINTS = "points";
    private static final String USER_TOTAL_POINTS = "totalPoints";
    private static final String STORY_COMPLETE = "storyComplete";
    private static final String PROGRESS = "progress";



    class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView storyNameItemView;
        public final ImageView storyStatusItemView;
        public final ProgressBar storyProgressItemView;
        public final ImageView storyImageItemView;
        private final Context context;
        final StoryAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter  The adapter that manages the the data and views
         *                 for the RecyclerView.
         */
        public StoryViewHolder(View itemView, StoryAdapter adapter) {
            super(itemView);
            context = itemView.getContext();
            storyNameItemView = itemView.findViewById(R.id.StoryTitelView);
            storyStatusItemView = itemView.findViewById(R.id.StoryStatusImage);
            storyProgressItemView = itemView.findViewById(R.id.storyProgressBar);
            storyImageItemView = itemView.findViewById(R.id.StoryImageView);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;
            int mPosition = getLayoutPosition();
            Story element = mStoryList.get(mPosition);
            if (element!=null){
                if(!element.isUnlocked()) {
                    openUnlockPopUp(element, mPosition);
                }
                else{
                    Fragment readstoryFragment = new Activity_read_story();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("storyInfo", element); // Key, value
                    bundle.putInt("storyIndex", mPosition);
                    readstoryFragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, readstoryFragment).commit();
                }
            } else {
//                intent =  new Intent(context, Detail.class);
            }
//            context.startActivity(intent);
        }

        private void openUnlockPopUp(Story element, int storyPosition){
            StoryUnlockPopup storyUnlockPopup = new StoryUnlockPopup();
            storyUnlockPopup.setStory(element, storyPosition);
            storyUnlockPopup.show(DataSingleton.getInstance().getStoryFragmentManager(), "Unlock Code");
        }
    }

    public StoryAdapter(Context context, LinkedList<Story> projectList, Fragment storymenuFragment) {
        this.storyMenuFragment = storymenuFragment;
        mInflater = LayoutInflater.from(context);
        this.mStoryList = projectList;
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.isColourBlind = preferences.getBoolean("colour_blind_theme", false);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public StoryAdapter.StoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.story_item, parent, false);

        return new StoryViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    //TODO probeer in de readStory de textviews colourblind mode vriendelijk te maken
    @Override
    public void onBindViewHolder(StoryAdapter.StoryViewHolder holder, int position) {
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        String mCurrent = mStoryList.get(position).getStoryName();
        holder.storyNameItemView.setText(mCurrent);
        int pCurrent = (int)preferences.getFloat(PROGRESS + position, 0);
        holder.storyProgressItemView.setProgress(pCurrent);
        boolean bCurrent = mStoryList.get(position).isUnlocked();
        if (!this.isColourBlind) {
            holder.itemView.setBackgroundResource(R.color.EsstelingRed);
            holder.storyProgressItemView.setBackgroundResource(R.color.EsstelingBlue);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorBlindText);
            holder.storyProgressItemView.setBackgroundResource(R.color.colorBlindBackground);
        }
        if (bCurrent) {
            holder.storyStatusItemView.setImageResource(R.drawable.arrow);
        } else {
            holder.storyStatusItemView.setImageResource(R.drawable.lock);
        }
        int iCurrent = mStoryList.get(position).getStoryImageResource();
        holder.storyImageItemView.setImageResource(iCurrent);


    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mStoryList.size();
    }
}