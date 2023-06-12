package com.example.noteyboi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class NotesActivity extends AppCompatActivity {
    //TODO: Test for and fix the reloading glitch
    int noteId = -1;
    private TextView nameView, noteView;
    private String noteName, noteDesc;
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
        fab.setOnClickListener(view -> {
            SaveNote();
            fab.startAnimation(fab_close);
            fab.hide();
        });
    }

    private void getIncomingIntent(){
        //Receive the data sent from the recycler view
        if (getIntent().hasExtra("noteName") && getIntent().hasExtra("noteDesc")) {
            noteName = getIntent().getStringExtra("noteName");
            noteDesc = getIntent().getStringExtra("noteDesc");
            noteId = getIntent().getIntExtra("ID", -1);
        } else {
            noteName = "";
            noteDesc = "";
        }
        setInfoEdit(noteName, noteDesc);
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
        if(hasTextChanged()){
            new AlertDialog.Builder(this)
                .setTitle("Do you want to leave without saving changes?")
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    SaveNote();
                    finish();
                })
                .setNeutralButton("Discard", (dialogInterface, i) -> finish())
//                    .setNeutralButton("Discard", new DialogInterface.OnClickListener(){
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
        //Since a text-watcher can only be defined when called
        final FloatingActionButton fabSave = findViewById(R.id.FABSave);

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(hasTextChanged()){
                if(!showSave){
                    fabSave.startAnimation(fab_open);
                    fabSave.show();
                    showSave = true;
                }
            } else {
                fabSave.startAnimation(fab_close);
                fabSave.hide();
                showSave = false;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Linkify.addLinks(editable, Linkify.ALL);}
    }

    private boolean hasTextChanged() {
        String textName =  nameView.getText().toString();
        String textNote =  noteView.getText().toString();
        return !textName.equals(noteName) || !textNote.equals(noteDesc);
    }

    public void SaveNote(){
        String textName =  nameView.getText().toString();
        String textNote =  noteView.getText().toString();
        coordinatorLayout = findViewById(R.id.mainNoteLayout);

        if (textName.equals("") && textNote.equals("")){
            Snackbar.make(coordinatorLayout, "Nothing to save", Snackbar.LENGTH_LONG)
                    .setAction("??", null).show();
        } else {
            if (noteId <= -1) {//Save new note
                //TODO: Make this a try catch using the main function
                noteId = mDatabaseHelper.addData(textName,textNote);

                if (noteId != -1){
                    Snackbar.make(coordinatorLayout, "Saved Successfully", Snackbar.LENGTH_LONG)
                            .setAction("??", null).show();
                    showSave = false;
                } else{
                    Snackbar.make(coordinatorLayout, "Something went wrong while saving", Snackbar.LENGTH_LONG)
                            .setAction("??", null).show();
                }
            } else {//Update existing note
                mDatabaseHelper.UpdateRow(textName,textNote, noteId);
                Snackbar.make(coordinatorLayout, "Updated Successfully", Snackbar.LENGTH_LONG)
                        .setAction("??", null).show();
                showSave = false;
            }
            noteName = textName;
            noteDesc = textNote;

        }
    }
}