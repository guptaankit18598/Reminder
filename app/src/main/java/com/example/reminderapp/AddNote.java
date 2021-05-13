package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static com.example.reminderapp.ObjectSerializer.serialize;

public class AddNote extends AppCompatActivity {
    EditText etadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etadd = findViewById(R.id.etaddnote);

    }
    public void ADD(View v) throws IOException {
        String inputnote="";
        try {
           inputnote = etadd.getText().toString();
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();
        }
        MainActivity.notes.add(inputnote);
        MainActivity.adapter.notifyItemInserted(MainActivity.notes.size());
        MainActivity.sharedPreferences.edit().putString("array", serialize(MainActivity.notes)).apply();
        Intent intent = new Intent(AddNote.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}