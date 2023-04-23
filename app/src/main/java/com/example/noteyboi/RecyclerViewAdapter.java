package com.example.noteyboi;

import android.annotation.SuppressLint;
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

    private ArrayList<String> mNoteNames = new ArrayList<>();
    private ArrayList<String> mNotes = new ArrayList<>();
    private ArrayList<Integer> mPosition = new ArrayList<>();
    private Context mContext;
    SQLManager mDatabaseHelper;
    private ArrayList<DatabaseObject> mDBObjects;



    //TODO:Reorganize this class
//    public RecyclerViewAdapter(Context context, ArrayList<String> noteNames, ArrayList<String> notes, ArrayList<Integer> positions) {
//        this.mContext = context;
//        this.mNoteNames = noteNames;
//        this.mNotes = notes;
//        this.mPosition = positions;
//    }

    public RecyclerViewAdapter(Context context, ArrayList<DatabaseObject> Objects) {
        this.mContext = context;
        this.mDBObjects = Objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create the view to insert the information into
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Populating the layout

        holder.noteName.setText(mDBObjects.get(position).getName());
        holder.note.setText(mDBObjects.get(position).getNote());
        final int ID = mDBObjects.get(position).getId();
        final int last = mPosition.get(mDBObjects.size() - 1);
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
                   mDatabaseHelper = new SQLManager(mContext);
//                   MainActivity.mNoteNames.remove(position);
//                   MainActivity.mNotes.remove(position);
//                   MainActivity.mID.remove(position);
//                   MainActivity.adapter.notifyDataSetChanged(); //Find a better way to do this
//                   MainActivity.adapter.notifyDataSetChanged(); Crashing the app
                   MainActivity.mDBObjects.remove(position);
                   MainActivity.adapter.notifyItemRemoved(position);
                   mDatabaseHelper.Delete(ID);
               }
           })
                    .setNegativeButton("No", null)
                    .show();
                 return true;
           }
       });

        //Passing the information into the activity that's made when it's clicked then starting it
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NotesActivity.class);
                intent.putExtra("noteName", mDBObjects.get(position).getName());
                intent.putExtra("noteDesc", mDBObjects.get(position).getNote());
                intent.putExtra("ID", mDBObjects.get(position).getId());
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
        TextView noteName;
        TextView note;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.name);
            note = itemView.findViewById(R.id.desc);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
