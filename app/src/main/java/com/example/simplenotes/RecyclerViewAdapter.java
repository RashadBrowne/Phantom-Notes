package com.example.simplenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "DatabaseHelper";
    private ArrayList<String> mNoteNames = new ArrayList<>();
    private ArrayList<String> mNotes = new ArrayList<>();
    private ArrayList<Integer> mPosition = new ArrayList<>();
    private Context mContext;
    DatabaseHelper mDatabaseHelper;

    public RecyclerViewAdapter(Context context, ArrayList<String> notenames, ArrayList<String> notes, ArrayList<Integer> positions) {
        this.mNoteNames = notenames;
        this.mNotes = notes;
        this.mPosition = positions;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create the view to insert the information into
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Populating the layout
        holder.notename.setText(mNoteNames.get(position));
        holder.note.setText(mNotes.get(position));
        //holder.position.setText(mNotes.get(position));
        final int dbPosition = mPosition.get(position);
        final int last = mPosition.get(mPosition.size() - 1);
        //Prompt for unsaved changes
        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               new AlertDialog.Builder(mContext)
                       .setTitle("Are you sure?")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   mDatabaseHelper = new DatabaseHelper(mContext);
                   MainActivity.mNoteNames.remove(position);
                   MainActivity.mNotes.remove(position);
                   MainActivity.mPosition.remove(position);
                   MainActivity.adapter.notifyDataSetChanged();
                   mDatabaseHelper.Delete(dbPosition);
               }
           })
                    .setNegativeButton("No", null)
                    .show();
                 return true;
           }
       });

        //Passing the information into the activity thats made when it's clicked then starting it
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NotesActivity.class);
                intent.putExtra("note_name", mNoteNames.get(position));
                intent.putExtra("note_desc", mNotes.get(position));
                intent.putExtra("position", position);
                intent.putExtra("trueposition", dbPosition);
                intent.putExtra("last", last);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Defining the objects in the layout
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
