package com.example.dendi.esportarena;

import android.*;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dendi.esportarena.db.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.id;

public class AddActivity extends AppCompatActivity {

    EditText title, desc, link, location, editDate;
    Button save, date, chooseBtn;
    ImageView imageView;
    static final int DIALOG_ID = 0;

    public static DatabaseHelper sqliteHelper;
    int days, months, years;
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //init EditText
        editDate = (EditText) findViewById(R.id.date_et);
        title = (EditText) findViewById(R.id.title_et);
        desc = (EditText) findViewById(R.id.desc_et);
        link = (EditText) findViewById(R.id.link_et);
        location = (EditText) findViewById(R.id.location_et);
        editDate = (EditText) findViewById(R.id.date_et);

        //init Button
        save = (Button) findViewById(R.id.save_btn);
        chooseBtn = (Button) findViewById(R.id.choose_btn);
        imageView = (ImageView) findViewById(R.id.photo);

        //init Calender
        Calendar cal = Calendar.getInstance();
        days = cal.get(Calendar.DAY_OF_MONTH);
        months = cal.get(Calendar.MONTH);
        years = cal.get(Calendar.YEAR);

        sqliteHelper = new DatabaseHelper(this, "eventDB.sqlite", null, 1);
        sqliteHelper.queryData("CREATE TABLE IF NOT EXISTS event(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR, " +
                "date VARCHAR," +
                "desc VARCHAR, " +
                "link VARCHAR, " +
                "location VARCHAR, " +
                "image BLOB)");

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqliteHelper.insertData(
                            title.getText().toString().trim(),
                            date.getText().toString().trim(),
                            desc.getText().toString().trim(),
                            link.getText().toString().trim(),
                            location.getText().toString().trim(),
                            imageViewToByte(imageView));

                    Toast.makeText(getApplicationContext(), "Added Event Successfully!", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    desc.setText("");
                    link.setText("");
                    location.setText("");
                    editDate.setText("");
                    imageView.setImageResource(R.drawable.photo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ShowDialogOnButtonClick();
    }

    public void ShowDialogOnButtonClick() {
        date = (Button) findViewById(R.id.date_btn);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int d) {
        if (d == DIALOG_ID)
            return new DatePickerDialog(this, dateSetListener, years, months, days);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            years = year;
            months = month + 1;
            days = dayOfMonth;
            editDate.setText(days + "/" + months + "/" + years);
        }
    };


//    //menyimpan detail event ke dalam database
//    public void saveEvent(View v){
//        String titleString = title.getText().toString();
//        String descString = desc.getText().toString();
//        String linkString = link.getText().toString();
//        String locationString = location.getText().toString();
//        String dateString = date.getText().toString();
//        byte[] imgArry = imageViewToByte(imageView);
//
//        DatabaseHelper sqlite = new DatabaseHelper(this, "esportarena", null, 1);
//        SQLiteDatabase db = sqlite.getWritableDatabase();
//
//        ContentValues querry = new ContentValues();
//        querry.put("title", titleString);
//        querry.put("desc", descString);
//        querry.put("date", dateString);
//        querry.put("link", linkString);
//        querry.put("location", locationString);
//        querry.put("img", imgArry);
//        db.insert("event",null,querry);
//        db.close();
//
//        Toast.makeText(this, "Event has been Added", Toast.LENGTH_SHORT).show();
//        title.setText("");
//        desc.setText("");
//        link.setText("");
//        location.setText("");
//        editDate.setText("");
//        imageView.setImageResource(R.drawable.photo);
//    }

    //convert file gambar ke byte typefiles
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
