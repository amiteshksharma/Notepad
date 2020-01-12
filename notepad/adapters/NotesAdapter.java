package com.example.notepad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.R;
import com.example.notepad.callbacks.NoteEventListener;
import com.example.notepad.model.Note;
import com.example.notepad.utils.NoteUtils;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private ArrayList<Note> notes;
    private Context context;
    private NoteEventListener listener;

    public NotesAdapter(Context context, ArrayList<Note> notes){
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        final Note note = getNote(position);
        if(note != null){
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNoteClick(note);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNote(int position){
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        TextView noteText, noteDate;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteDate = itemView.findViewById(R.id.noteDate);
            noteText = itemView.findViewById(R.id.noteText);
        }
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }
}
