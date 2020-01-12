package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notepad.db.NotesDB;
import com.example.notepad.db.NotesDao;
import com.example.notepad.model.Note;
import com.example.notepad.model.PaintView;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    private EditText inputNote;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_KEY = "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        inputNote = findViewById(R.id.inputNote);
        dao = NotesDB.getInstance(this).notesDao();
        if(getIntent().getExtras() != null){
            int id = getIntent().getIntExtra(NOTE_EXTRA_KEY, 0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        } else temp = new Note();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.saveNote)
            onSaveNote();
        if(id == R.id.drawNote)
            onDrawNote();
        return super.onOptionsItemSelected(item);
    }

    private void onDrawNote() {
        PaintView paintView = new PaintView(this);
        setContentView(paintView);
    }

    private void onSaveNote() {
        String text = inputNote.getText().toString();
        if(!text.isEmpty()){
            long date = new Date().getTime();

            temp.setNoteDate(date);
            temp.setNoteText(text);
            dao.insertNote(temp);

            finish();
        } else Toast.makeText(getApplicationContext(),
                "Please enter data", Toast.LENGTH_SHORT).show();
    }
}
