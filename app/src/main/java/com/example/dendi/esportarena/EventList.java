package com.example.dendi.esportarena;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    ListView listView;
    ArrayList<Event> list;
    EventListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        listView = (ListView) findViewById(R.id.eventList);
        list = new ArrayList<>();
        adapter = new EventListAdapter(this, R.layout.event_item, list);
        listView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = AddActivity.sqliteHelper.getData("SELECT * FROM event");
        list.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String date = cursor.getString(2);
            String desc = cursor.getString(3);
            String link = cursor.getString(4);
            String location = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Event(id, title, date, desc, link, location, image));
        }
        adapter.notifyDataSetChanged();

        final Intent intent = new Intent(EventList.this, DetailEventActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventItem = (Event) listView.getItemAtPosition(position);
                intent.putExtra("id",position);
                intent.putExtra("title", eventItem.getTitle());
                intent.putExtra("location", eventItem.getLocation());
                intent.putExtra("date", eventItem.getDate());
                intent.putExtra("desc", eventItem.getDesc());
                intent.putExtra("link", eventItem.getLink());
                intent.putExtra("image", eventItem.getImage());
                startActivity(intent);
            }
        });
    }
}
