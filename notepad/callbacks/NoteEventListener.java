package com.example.notepad.callbacks;

import com.example.notepad.model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
