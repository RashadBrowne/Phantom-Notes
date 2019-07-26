package com.example.noteyboi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NotesActivity extends AppCompatActivity {
////////////////////////////////////////////////////////////////////////////////////////////////////
    int noteId = -1;
    private TextView nameview;
    private TextView noteview;
////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

////////////////////////////////////////////////////////////////////////////////////////////////////
        getIncomingIntentEdit();
        nameview = findViewById(R.id.NoteName);
        noteview = findViewById(R.id.Notes);


        FloatingActionButton fab = findViewById(R.id.fabsave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                        .setAction("??", null).show();
                String textname =  nameview.getText().toString();
                String textnote =  noteview.getText().toString();
                MainActivity.mNoteNames.set(noteId, textname);
                MainActivity.mNotes.set(noteId, textnote);
                MainActivity.adapter.notifyDataSetChanged();
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
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
