package com.example.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewNotes=findViewById(R.id.main_listview_notes);

    }

    public void createNewNote(View view)
    {
        startActivity(new Intent(this,NoteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();


        mListViewNotes.setAdapter(null);
        final ArrayList<Note> notes=Utilities.getAllSavedNotes(this);

        if(notes==null || notes.size()==0)
        {
            Toast.makeText(this, "You have no saved content", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            final NoteAdapter na=new NoteAdapter(this,R.layout.item_notes,notes);
            mListViewNotes.setAdapter(na);

            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?>parent,View view,int position,long id) {
                    String fileName=((Note)mListViewNotes.getItemAtPosition(position)).getDateTime()+Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent=new Intent(getApplicationContext(),NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE",fileName);
                    startActivity(viewNoteIntent);
                }
            });

        }
    }

    public void navigateToTasker(View view)
    {
        startActivity(new Intent(this,TaskerActivity.class));
    }

}
