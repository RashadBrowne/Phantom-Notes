package com.example.noteyboi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class NotesActivity extends AppCompatActivity {
    int noteId = -1;
    private TextView nameview, noteview;
    private Animation fab_open,fab_close;
    boolean open = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getIncomingIntentEdit();
        nameview = findViewById(R.id.NoteName);
        noteview = findViewById(R.id.Notes);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);


        final FloatingActionButton fab = findViewById(R.id.fabsave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textname =  nameview.getText().toString();
                String textnote =  noteview.getText().toString();

                Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                        .setAction("??", null).show();

                if (noteId <= -1) {
                    MainActivity.mNoteNames.add("");
                    MainActivity.mNotes.add("");
                    noteId= MainActivity.mNoteNames.size() - 1;
                }

                MainActivity.mNoteNames.set(noteId, textname);
                MainActivity.mNotes.set(noteId, textnote);
                MainActivity.adapter.notifyDataSetChanged();
                fab.startAnimation(fab_close);
                fab.hide();
                open = false;
            }
        });
    }

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
        final FloatingActionButton fabsave = findViewById(R.id.fabsave);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!open){
                    fabsave.startAnimation(fab_open);
                    fabsave.show();
                    open = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!open){
                    fabsave.startAnimation(fab_open);
                    fabsave.show();
                    open = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }


}
///if(text.equals("")){}

