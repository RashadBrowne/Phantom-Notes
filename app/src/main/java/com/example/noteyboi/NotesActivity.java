package com.example.noteyboi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class NotesActivity extends AppCompatActivity {
    //TODO: Clean up this database code, it should be entirely in databasehelper
    //TODO: Test for and fix the reloading glitch
    int noteId = -1;
    private TextView nameView, noteView;
    private Animation fab_open,fab_close;
    boolean showSave = false;
    CoordinatorLayout coordinatorLayout;
    SQLManager mDatabaseHelper;


    //TODO: Work on autocorrect in the settings menu
    //textAutoCorrect or textNoSuggestions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Initialize
        mDatabaseHelper = new SQLManager(this);

        nameView = findViewById(R.id.NoteName);
        noteView = findViewById(R.id.Notes);

        getIncomingIntent();

        //Configure
        noteView.setLinksClickable(true);
        noteView.setAutoLinkMask(Linkify.WEB_URLS);
        //If the edit text contains previous text with potential links
        //Linkify has options "WEB_URLS, PHONE_NUMBERS, EMAIL_ADDRESSES"
        Linkify.addLinks(noteView, Linkify.ALL);
        noteView.setMovementMethod(LinkMovementMethod.getInstance());

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        final FloatingActionButton fab = findViewById(R.id.FABSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveNote();
                fab.startAnimation(fab_close);
                fab.hide();
            }
        });
    }

    private void getIncomingIntent(){
        //Receive the data sent from the recycler view
        if (getIntent().hasExtra("noteName") && getIntent().hasExtra("noteDesc")) {
            String noteName = getIntent().getStringExtra("noteName");
            String noteDesc = getIntent().getStringExtra("noteDesc");
//            noteId = getIntent().getIntExtra("position", -1);
//            truePosition = getIntent().getIntExtra("truePosition", 0);
            noteId = getIntent().getIntExtra("ID", -1);
//            lastId = getIntent().getIntExtra("last", 0);

            setInfoEdit(noteName, noteDesc);
        } else {
            setInfoEdit("", "");
        }
    }

    private void setInfoEdit(String Name, String Note){
        //As we populate more items its better to make a class to do it
        nameView.setText(Name);
        noteView.setText(Note);
        nameView.addTextChangedListener(new GenericTextWatcher());
        noteView.addTextChangedListener(new GenericTextWatcher());
    }

    @Override
    public void onBackPressed() {
        //Display a save prompt for the user
        if(showSave){
            new AlertDialog.Builder(this)
                .setTitle("Do you want to leave without saving changes?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveNote();
                        finish();
                    }
                })
                .setNeutralButton("Discard", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show();
        }
        else{
            super.onBackPressed();
        }
    }

    private class GenericTextWatcher implements TextWatcher {
        //Simplifies the code instead of making two duplicated text watcher
        //Since a textwatcher can only be defined when called
        final FloatingActionButton fabSave = findViewById(R.id.FABSave);

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //TODO: Add a check to make sure its not the same as the text in the db
            if(!showSave){
                fabSave.startAnimation(fab_open);
                fabSave.show();
                showSave = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Linkify.addLinks(editable, Linkify.ALL);}
    }

    public void SaveNote(){
        String textName =  nameView.getText().toString();
        String textNote =  noteView.getText().toString();
//        lastId += 1;
        coordinatorLayout = findViewById(R.id.mainNoteLayout);

        if (textName.equals("") && textNote.equals("")){
            Snackbar.make(coordinatorLayout, "Nothing to save", Snackbar.LENGTH_LONG)
                    .setAction("??", null).show();
//            return;
        } else {
            if (noteId <= -1) {//Save new note
//                noteId = MainActivity.mNoteNames.size();

                //TODO: Make this a try catch using the main function

                noteId = mDatabaseHelper.addData(textName,textNote);

                if (noteId != -1){
                    createToastMessage("Saved Successfully");
                } else{
                    createToastMessage("Something went wrong while saving");
                }
//                AddData(textName,textNote);

//                Cursor data = mDatabaseHelper.getData();
//                truePosition = data.getCount() - 1;
//                while(data.moveToNext()) {
//                    lastPosition.add(data.getInt(0));
//                }
//                truePosition = lastPosition.get(lastPosition.size() - 1);
//                MainActivity.mPosition.add(truePosition);
            } else {//Update existing note
//                mDatabaseHelper.UpdateRow(textName,textNote, truePosition);
                mDatabaseHelper.UpdateRow(textName,textNote, noteId);
            }
            showSave = false;
            //TODO: Convert to toast?
            Snackbar.make(coordinatorLayout, "Saved", Snackbar.LENGTH_LONG)
                    .setAction("??", null).show();
        }
    }

    private void createToastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}