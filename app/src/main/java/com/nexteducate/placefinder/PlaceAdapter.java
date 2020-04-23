package com.nexteducate.placefinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nexteducate.placefinder.db.User;
import com.nexteducate.placefinder.utils.Utils;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.CustomHolder> {
    private ArrayList<User> usersArrayList;
    private OnPlaceClickListener onPlaceClickListener;
    private String placeLocationValue;
    private Context context;

    public PlaceAdapter(Context context, OnPlaceClickListener onPlaceClickListener) {
        this.context = context;
        this.onPlaceClickListener = onPlaceClickListener;
    }

    public void setUsersArrayList(ArrayList<User> usersArrayList, String placeLocation) {
        this.usersArrayList = usersArrayList;
        this.placeLocationValue = placeLocation;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewallllst, parent, false);
        return new CustomHolder(listItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(usersArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        if (usersArrayList == null)
            return 0;
        else {
            return usersArrayList.size();
        }
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        TextView description, placeName, placeLocation;
        ImageView place;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {
            place = itemView.findViewById(R.id.imageLocation);
            description = itemView.findViewById(R.id.placeDescription);
            placeName = itemView.findViewById(R.id.placeName);
            placeLocation = itemView.findViewById(R.id.placeLocation);
        }

        private void bind(final User user) {
            description.setText(user.getPlace_description());
            Log.d("count", user.getThumbnail());
            final String subPath = user.getThumbnail().substring(user.getThumbnail().lastIndexOf("/") + 1);
            Log.d("count", subPath);
            Glide.with(context).load(Utils.loadImageBitmap(context, subPath.trim())).into(place);
            placeName.setText(user.getPlace_title());
            placeLocation.setText(placeLocationValue);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPlaceClickListener.clickPlace(user.getPlace_description(), user.getPlace_address(), user.getPlace_latitude(), user.getPlace_longitude(),

                            user.getPlace(), place,subPath,ViewCompat.getTransitionName(place),subPath);
                }
            });
            ViewCompat.setTransitionName(place, subPath);
//

        }
    }
}
