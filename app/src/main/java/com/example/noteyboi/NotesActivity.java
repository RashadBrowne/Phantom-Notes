package com.example.noteyboi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.database.Cursor;
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
    int lastid = 0;
    int trueposition = 0;
    private TextView nameview, noteview;
    private Animation fab_open,fab_close;
    boolean showSave = false;
    CoordinatorLayout coordinatorLayout;
    OldDatabaseHelper mDatabaseHelper;
    static ArrayList<Integer> lastpos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getIncomingIntentEdit();
        mDatabaseHelper = new OldDatabaseHelper(this);
        nameview = findViewById(R.id.NoteName);
        noteview = findViewById(R.id.Notes);

        noteview.setLinksClickable(true);
        noteview.setAutoLinkMask(Linkify.WEB_URLS);
        //If the edit text contains previous text with potential links
        //Linkify has options "WEB_URLS, PHONE_NUMBERS, EMAIL_ADDRESSES"
        Linkify.addLinks(noteview   , Linkify.ALL);
        noteview.setMovementMethod(LinkMovementMethod.getInstance());

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        final FloatingActionButton fab = findViewById(R.id.fabsave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveNote();
                fab.startAnimation(fab_close);
                fab.hide();
            }
        });
    }

    private void getIncomingIntentEdit(){
        //Receive info from the recycler view
        if (getIntent().hasExtra("note_name") && getIntent().hasExtra("note_desc")) {
            String noteName = getIntent().getStringExtra("note_name");
            String noteDesc = getIntent().getStringExtra("note_desc");
            noteId = getIntent().getIntExtra("position", -1);
            trueposition = getIntent().getIntExtra("trueposition", 0);
            lastid = getIntent().getIntExtra("last", 0);

            setInfoEdit(noteName, noteDesc);
        } else {
            setInfoEdit("", "");
        }
    }

    private void setInfoEdit(String Name, String Desc){
        //As we populate more items its better to make a class to do it
        TextView name = findViewById(R.id.NoteName);
        TextView desc = findViewById(R.id.Notes);
        name.setText(Name);
        desc.setText(Desc);
        name.addTextChangedListener(new GenericTextWatcher());
        desc.addTextChangedListener(new GenericTextWatcher());
    }

    @Override
    public void onBackPressed() {
        //Make sure the user saves changes if they want to
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
//            MainActivity.refreshRecyclerView();
            super.onBackPressed();
        }
    }

    private class GenericTextWatcher implements TextWatcher {
        //Simplifies the code instead of making two duplicated text watcher
        //Since a textwatcher can only be defined when called
        final FloatingActionButton fabsave = findViewById(R.id.fabsave);

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!showSave){
                fabsave.startAnimation(fab_open);
                fabsave.show();
                showSave = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Linkify.addLinks(editable, Linkify.ALL);}
    }

    public void SaveNote(){
        String textname =  nameview.getText().toString();
        String textnote =  noteview.getText().toString();
        lastid += 1;
        coordinatorLayout = findViewById(R.id.mainnotelayout);

        if (textname.equals("") && textnote.equals("")){
            Snackbar.make(coordinatorLayout, "Nothing to save", Snackbar.LENGTH_LONG)
                    .setAction("??", null).show();
        } else {
            if (noteId <= -1) {
                MainActivity.mNoteNames.add("");
                MainActivity.mNotes.add("");
                noteId = MainActivity.mNoteNames.size() - 1;

                //TODO: Make this a try catch using the main function
                AddData(textname,textnote);

                Cursor data = mDatabaseHelper.getData();
                while(data.moveToNext()) {
                    lastpos.add(data.getInt(0));
                }
                trueposition = lastid = lastpos.get(lastpos.size() - 1);
                MainActivity.mPosition.add(lastid);
            } else {
                mDatabaseHelper.UpdateRow(textname,textnote, trueposition);
            }

            MainActivity.mNoteNames.set(noteId, textname);
            MainActivity.mNotes.set(noteId, textnote);
//            MainActivity.adapter.notifyDataSetChanged();
            showSave = false;
            Snackbar.make(coordinatorLayout, "Saved", Snackbar.LENGTH_LONG)
                    .setAction("??", null).show();
        }
    }

    //for sqlite
    private void AddData(String Name, String Note){
        boolean insertData = mDatabaseHelper.addData(Name,Note);

        if (insertData){
            toastMessage("Saved Successfully");
        } else{
            toastMessage("Something went wrong while saving");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}