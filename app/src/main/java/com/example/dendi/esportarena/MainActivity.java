package com.example.dendi.esportarena;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.dendi.esportarena.AuthForm.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AddActivity {

    ListView listView;
    private FirebaseAuth mAuth;
    private Button logout,ref;
    ArrayList<Event> list;
    EventListAdapter adapter = null;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = (Button) findViewById(R.id.signout_btn);
        list = new ArrayList<>();
        adapter = new EventListAdapter(this, R.layout.event_item, list);
        listView = (ListView) findViewById(R.id.eventList);
        listView.setAdapter(adapter);
        ref = (Button) findViewById(R.id.refresh_btn);


     try {
        Cursor cursor = AddActivity.sqliteHelper.getData("SELECT * FROM event");
        list.clear();
        cursor.moveToFirst();
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
    } catch (Exception e ){
         e.printStackTrace();
     }




        final Intent intent = new Intent(MainActivity.this, DetailEventActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event eventItem = (Event) listView.getItemAtPosition(position);
                intent.putExtra("id", position);
                intent.putExtra("title", eventItem.getTitle());
                intent.putExtra("location", eventItem.getLocation());
                intent.putExtra("date", eventItem.getDate());
                intent.putExtra("desc", eventItem.getDesc());
                intent.putExtra("link", eventItem.getLink());
                intent.putExtra("image", eventItem.getImage());
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.add_fab);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEventsData();
            }
        });
    }

    private void getEventsData() {
        Cursor cursor = AddActivity.sqliteHelper.getData("SELECT * FROM event");
        list.clear();
        while (cursor.moveToNext()) {
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

    }

}

