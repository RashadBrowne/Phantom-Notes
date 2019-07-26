package com.example.noteyboi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {
////////////////////////////////////////////////////////////////////////////////////////////////////
    int noteId = -1;
////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

////////////////////////////////////////////////////////////////////////////////////////////////////
        getIncomingIntentEdit();

        FloatingActionButton fab = findViewById(R.id.fabsave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                        .setAction("??", null).show();
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent);
                Log.d("Debug", "onTextChanged: Click");
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getIncomingIntentEdit(){
        if (getIntent().hasExtra("note_name") && getIntent().hasExtra("note_desc")) {
            String noteName = getIntent().getStringExtra("note_name");
            String noteDesc = getIntent().getStringExtra("note_desc");
            noteId = getIntent().getIntExtra("position", -1);
            setInfoEdit(noteName, noteDesc);
        } else {
            setInfoEdit("", "");
        }
    }

    private void setInfoEdit(String Name, String Desc){
        TextView name = findViewById(R.id.NoteName);
        TextView desc = findViewById(R.id.Notes);
        name.setText(Name);
        desc.setText(Desc);


        if (noteId != -1){
            Log.d("Loaded", "onTextChanged: Right id");
        }else{
            MainActivity.mNoteNames.add("");
            MainActivity.mNotes.add("");
            noteId= MainActivity.mNoteNames.size() - 1;
            MainActivity.adapter.notifyDataSetChanged();
        }



        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.mNoteNames.set(noteId, String.valueOf(charSequence));
                MainActivity.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.mNotes.set(noteId, String.valueOf(charSequence));
                MainActivity.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
