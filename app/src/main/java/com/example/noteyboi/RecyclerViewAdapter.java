package com.example.noteyboi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private final Context mContext;
    SQLManager mDatabaseHelper;
    private final ArrayList<DatabaseObject> mDBObjects;


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
        Log.d("FFS", "onBindViewHolder: " + mDBObjects.get(position).getName());
        holder.note.setText(mDBObjects.get(position).getNote());
        final int ID = mDBObjects.get(position).getId();
//        int last = mPosition.get(mDBObjects.size() - 1);

        //Prompt for unsaved changes
        holder.parentLayout.setOnLongClickListener(view -> {
            new AlertDialog.Builder(mContext)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        mDatabaseHelper = new SQLManager(mContext);
                        MainActivity.mDBObjects.remove(position);
//                        MainActivity.adapter.notifyItemRemoved(position);
                        MainActivity.adapter.notifyDataSetChanged();
                        mDatabaseHelper.Delete(ID);
                    })
                 .setNegativeButton("No", null)
                 .show();
              return true;
        });

        //Passing the information into the activity that's made when it's clicked then starting it
        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, NotesActivity.class);
            intent.putExtra("noteName", mDBObjects.get(position).getName());
            intent.putExtra("noteDesc", mDBObjects.get(position).getNote());
            intent.putExtra("ID", mDBObjects.get(position).getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDBObjects.size();
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
