package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class EditNote extends AppCompatActivity {
EditText edit;
String note = " ";
int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        edit = findViewById(R.id.eteditnote);
        position = getIntent().getIntExtra("pos", -1);
        if (position != -1) {
            edit.setText(MainActivity.notes.get(position));
        } else {
            edit.setText("NOTE");
        }
    }
    public void ADD(View v) throws IOException {
        if (position != -1) {
            MainActivity.notes.set(position, edit.getText().toString());
            MainActivity.sharedPreferences.edit().putString("array", ObjectSerializer.serialize(MainActivity.notes)).apply();
            MainActivity.adapter.notifyDataSetChanged();
        }
        finish();
    }


}