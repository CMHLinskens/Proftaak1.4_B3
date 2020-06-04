package com.example.esstelingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.text.TextDirectionHeuristic;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.esstelingapp.Achievement;
import com.example.esstelingapp.R;
import com.example.esstelingapp.data.DataSingleton;
import com.example.esstelingapp.data.ThemeState;

import java.util.LinkedList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private final LinkedList<Achievement> mAchievementList;
    private final LayoutInflater mInflater;
    private static final String PREFS_NAME = "prefs";
    private boolean isColourBlind;


    class AchievementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView achievementNameItemView;
        public final ImageView achievementStatusItemView;
        public final ProgressBar achievementProgressItemView;
//        public final ImageView achievementImageItemView;
        private final Context context;
        final AchievementAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public AchievementViewHolder(View itemView, AchievementAdapter adapter) {
            super(itemView);
            context= itemView.getContext();
            achievementNameItemView = itemView.findViewById(R.id.AchievementNameView);
            achievementStatusItemView = itemView.findViewById(R.id.achievmentStatusView);
            achievementProgressItemView = itemView.findViewById(R.id.AchievementProgressBar);
//            achievementImageItemView = itemView.findViewById(R.id.StoryImageView);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;
            int mPosition = getLayoutPosition();
            Achievement element = mAchievementList.get(mPosition);
            if (element!=null){
                if(!element.getAchievementStatus()) {
                    // TODO open pop-up
                }
//                intent =  new Intent(context, Detail.class);
//                intent.putExtra("Project_Name_Key", element.getProjectName());
//                intent.putExtra("Project_Year_Key", element.getProjectYear());
//                intent.putExtra("Project_Desc_Key", element.getProjectFullDescription());
//                intent.putExtra("Project_Addition_Key", element.getAdditionToProject());
//                intent.putExtra("Project_Image_Key", element.getProjectImageURL());
            }
            else{
//                intent =  new Intent(context, Detail.class);
            }
//            context.startActivity(intent);
        }
    }

    public AchievementAdapter(Context context, LinkedList<Achievement> projectList) {
        mInflater = LayoutInflater.from(context);
        this.mAchievementList = projectList;
        SharedPreferences preferences = DataSingleton.getInstance().getMainContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.isColourBlind = preferences.getBoolean("colour_blind_theme", false);
    }


    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
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
    public AchievementAdapter.AchievementViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.achievement_item, parent, false);
        return new AchievementViewHolder(mItemView, this);
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
    @Override
    public void onBindViewHolder(AchievementAdapter.AchievementViewHolder holder, int position) {
        Achievement current = mAchievementList.get(position);
        String mCurrent = current.getAchievementName();
        holder.achievementNameItemView.setText(mCurrent);
        int pCurrent = current.getAchievementProgress();
        holder.achievementProgressItemView.setProgress(pCurrent);
        boolean bCurrent = current.getAchievementStatus();
        if(!this.isColourBlind){
            holder.itemView.setBackgroundResource(R.color.EsstelingRed);
            holder.achievementProgressItemView.setBackgroundResource(R.color.EsstelingBlue);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorBlindText);
            holder.achievementProgressItemView.setBackgroundResource(R.color.colorBlindBackground);
        }

        if (bCurrent){
            holder.achievementStatusItemView.setImageResource(R.drawable.star);
        }
        else {
            holder.achievementStatusItemView.setImageResource(R.drawable.lock);
        }
//        int iCurrent = current.getAchievementImageURL();
//
//        holder.achievementImageItemView.setImageResource(iCurrent);

//

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mAchievementList.size();
    }
}