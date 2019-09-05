package com.example.simplenotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> mNoteNames = new ArrayList<>();
    static ArrayList<String> mNotes = new ArrayList<>();
    static ArrayList<Integer> mPosition = new ArrayList<>();
    static RecyclerViewAdapter adapter;
    static RecyclerView recyclerView;
    DatabaseHelper mDatabaseHelper;
////////////////////////////////////////////////////////////////////////////////////////////////////


     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Toolbar toolbar = findViewById(R.id.toolbar);
         //setSupportActionBar(toolbar);

         ////////////////////////////////////////////////////////////////////////////////////////////
         mDatabaseHelper = new DatabaseHelper(this);
         Loadinfo();
         //So I can update the recycler view from other activities
         recyclerView = findViewById(R.id.recycler_view);
         adapter = new RecyclerViewAdapter(this, mNoteNames, mNotes, mPosition);
         recyclerView.setAdapter(adapter);
         ////////////////////////////////////////////////////////////////////////////////////////////

         FloatingActionButton fab = findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Snackbar.make(view, "Created New Note", Snackbar.LENGTH_LONG)
                         .setAction("??", null).show();
                 Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                 startActivity(intent);
             }
         });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_settings2) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private void Loadinfo(){
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            mPosition.add(data.getInt(0));//get items from column zero
            mNoteNames.add(data.getString(1));//get items from column one
            mNotes.add(data.getString(2));//get items from column two
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNoteNames, mNotes, mPosition);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}