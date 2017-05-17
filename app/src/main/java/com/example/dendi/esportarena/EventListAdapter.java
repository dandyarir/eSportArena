package com.example.dendi.esportarena;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dendi on 5/17/2017.
 */

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Event> eventsList;

    public EventListAdapter(Context context, int layout, ArrayList<Event> eventsList) {
        this.context = context;
        this.layout = layout;
        this.eventsList = eventsList;
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textTitle1, textLocation1, textDate1, textLink1, textDesc1;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textTitle1 = (TextView) row.findViewById(R.id.textTitle);
            holder.textLocation1 = (TextView) row.findViewById(R.id.textLocation);
            holder.textDate1 = (TextView) row.findViewById(R.id.textDate);
            holder.textLink1 = (TextView) row.findViewById(R.id.textLink);
            holder.textDesc1 = (TextView) row.findViewById(R.id.textDesc);
            holder.imageView = (ImageView) row.findViewById(R.id.imageEvent);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Event event = eventsList.get(position);

        holder.textTitle1.setText(event.getTitle());
        holder.textLocation1.setText(event.getLocation());
        holder.textDate1.setText(event.getDate());
        holder.textDesc1.setText(event.getDesc());
        holder.textLink1.setText(event.getLink());

        byte[] eventImage = event.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(eventImage, 0, eventImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
