package com.example.noteyboi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mNoteNames = new ArrayList<>();
    private ArrayList<String> mNotes = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> notenames, ArrayList<String> notes) {
        this.mNoteNames = notenames;
        this.mNotes = notes;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_layout,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.notename.setText(mNoteNames.get(position));
        holder.note.setText(mNotes.get(position));




        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               new AlertDialog.Builder(mContext)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure?")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   MainActivity.mNoteNames.remove(position);
                   MainActivity.mNotes.remove(position);
                   MainActivity.adapter.notifyDataSetChanged();
               }
           })
                    .setNegativeButton("No", null)
                    .show();
                 return true;
           }
       });


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NotesActivity.class);
                intent.putExtra("note_name", mNoteNames.get(position));
                intent.putExtra("note_desc", mNotes.get(position));
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notename;
        TextView note;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notename = itemView.findViewById(R.id.name);
            note = itemView.findViewById(R.id.desc);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
