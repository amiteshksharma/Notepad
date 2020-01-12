package com.example.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.notepad.adapters.NotesAdapter;
import com.example.notepad.callbacks.NoteEventListener;
import com.example.notepad.db.NotesDB;
import com.example.notepad.db.NotesDao;
import com.example.notepad.model.Note;
import com.example.notepad.utils.NoteUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.notepad.EditNoteActivity.NOTE_EXTRA_KEY;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter notesAdapter;
    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes();
        notes.addAll(list);

        notesAdapter = new NotesAdapter(this, notes);

        notesAdapter.setListener(this);

        recyclerView.setAdapter(notesAdapter);
    }

    private void onAddNewNote() {
        startActivity(new Intent(this, EditNoteActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent edit = new Intent(this, EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_KEY, note.getId());
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(final Note note) {
        new AlertDialog.Builder(this).setTitle(R.string.app_name).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.deleteNote(note);
                loadNotes();

            }
        }).setNegativeButton("share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String text = note.getNoteText() + "\n Create on: "
                        + NoteUtils.dateFromLong(note.getNoteDate()) + "By: " + getString(R.string.app_name);

                share.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(share);


            }
        }).create().show();
    }
}
