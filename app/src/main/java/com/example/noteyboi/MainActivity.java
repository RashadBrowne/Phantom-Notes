package com.example.noteyboi;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLManager mDatabaseHelper;
    static ArrayList<DatabaseObject> mDBObjects = new ArrayList<>();
    static RecyclerViewAdapter adapter;
    static RecyclerView recyclerView;


////////////////////////////////////////////////////////////////////////////////////////////////////
////Activity Lifecycle Modifiers////////////////////////////////////////////////////////////////////
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         ///////////////////////////////////////////////////////////////////////////////////////////
         mDatabaseHelper = new SQLManager(this);
         loadRecyclerViewData();
         ///////////////////////////////////////////////////////////////////////////////////////////

         FloatingActionButton fab = findViewById(R.id.fab);
         fab.setOnClickListener(view -> {
             Snackbar.make(view, "Created New Note", Snackbar.LENGTH_LONG)
                     .setAction("??", null).show();
             Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
             startActivity(intent);
         });
     }

     @Override
     protected void onResume(){
         super.onResume();
         refreshRecyclerView();
     }


////////////////////////////////////////////////////////////////////////////////////////////////////
////Recycler View Code//////////////////////////////////////////////////////////////////////////////

    public void refreshRecyclerView(){
        mDBObjects.removeAll(mDBObjects);
        loadRecyclerViewData();
    }

    public void loadRecyclerViewData(){
        mDBObjects = mDatabaseHelper.getData();
        initRecyclerView();
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        adapter = new RecyclerViewAdapter(this, mDBObjects);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
////Option Menu Code////////////////////////////////////////////////////////////////////////////////
    //TODO: Add to the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //TODO: Convert to switch statement
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
}

