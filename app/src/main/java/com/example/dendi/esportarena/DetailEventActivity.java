package com.example.dendi.esportarena;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailEventActivity extends EventList {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Intent i = getIntent();
        String eventTitle = i.getStringExtra("title");
        String eventLocation = i.getStringExtra("location");
        String eventDate = i.getStringExtra("date");
        String eventDesc = i.getStringExtra("desc");
        String eventLink = i.getStringExtra("link");

        byte[] eventImage = i.getByteArrayExtra("image");

        ImageView image = (ImageView) findViewById(R.id.imageEvent);
        TextView title = (TextView) findViewById(R.id.textTitle);
        TextView location = (TextView) findViewById(R.id.textLocation);
        TextView date = (TextView) findViewById(R.id.textDate);
        TextView desc = (TextView) findViewById(R.id.textDesc);
        TextView link = (TextView) findViewById(R.id.textLink);

        title.setText(eventTitle);
        location.setText(eventLocation);
        date.setText(eventDate);
        desc.setText(eventDesc);
        link.setText(eventLink);
        //image.setImageResource(eventImage);

    }
}
