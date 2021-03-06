package com.example.notepad.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "notes")
public class Note {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "text")
    private String noteText;
    @ColumnInfo (name = "date")
    private long noteDate;

    public Note(String noteText, long noteDate){
        this.noteText = noteText;
        this.noteDate = noteDate;
    }

    @Ignore
    public Note() {}

    public long getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteDate=" + noteDate +
                '}';
    }
}
