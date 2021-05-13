package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
 RecyclerView rv;
 static RvAdapter adapter;
 static ArrayList<String> notes = new ArrayList<>();
 static SharedPreferences sharedPreferences;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(MainActivity.this,AddNote.class);
            startActivity(intent);

            return true;
        }
        return false;
    }

    public void SaveData() throws IOException {
        sharedPreferences.edit().putString("array",ObjectSerializer.serialize(notes)).apply();
    }
    public void LoadData() {
        try {
            notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("array", " "));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv= findViewById(R.id.rv);
        sharedPreferences = this.getSharedPreferences("com.example.reminderapp", MODE_PRIVATE);
        LoadData();
        if(notes.size() == 0) {
            notes.add("Sample Note");
            try {
                SaveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        adapter= new RvAdapter(notes);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter);

            adapter.itemClicked(new RvAdapter.ItemClickedListener() {
                @Override
                public void itemClicked(int position) {
            Intent intent = new Intent(MainActivity.this, EditNote.class);
            intent.putExtra("pos", position);
            startActivity(intent);
                }

                @Override
                public void deleteClicked(int position) {
                    if (position >= 0 && position < notes.size()) {
                       AlertDialog dialog = new  AlertDialog.Builder(MainActivity.this)
                               .setMessage("You are going to delete a Note")
                               .setTitle("Are you Sure you want to delete?")
                               .setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       notes.remove(position);
                                       try {
                                           SaveData();
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                       adapter.notifyDataSetChanged();
                                   }
                               }).setNegativeButton("No", null).create();
                        dialog.show();
                    }
                }
            });
        }

}